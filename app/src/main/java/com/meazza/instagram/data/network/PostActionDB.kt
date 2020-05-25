package com.meazza.instagram.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.meazza.instagram.data.model.Post
import com.meazza.instagram.util.POST_IMAGE_URL
import com.meazza.instagram.util.POST_REF
import kotlinx.coroutines.tasks.await

object PostActionDB {

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val storage by lazy { FirebaseStorage.getInstance().reference }
    private val currentUserId by lazy { FirebaseAuth.getInstance().currentUser?.uid }

    private val postRef = db.collection(POST_REF)

    suspend fun createPost(post: Post, image: ByteArray) {
        postRef.document(post.userId!!).collection(post.userId).add(post)
            .addOnSuccessListener { document ->
                val postId = document.id
                storage.child("$POST_REF/${post.userId}/$postId").putBytes(image)
                    .addOnSuccessListener {
                        it.storage.downloadUrl.addOnSuccessListener { uri ->
                            addImageUrl(post.userId, postId, uri.toString())
                        }
                    }
            }.await()
    }

    private fun addImageUrl(userId: String, postId: String, imageUrl: String) {
        postRef.document(userId).collection(userId).document(postId)
            .update(POST_IMAGE_URL, imageUrl)
    }

    suspend fun getPosts(): MutableList<Post> {

        val posts = mutableListOf<Post>()
        val query = postRef.document(currentUserId!!).collection(currentUserId!!).get().await()

        for (document in query) {
            val post = document.toObject(Post::class.java)
            posts.add(post)
        }

        return posts
    }
}