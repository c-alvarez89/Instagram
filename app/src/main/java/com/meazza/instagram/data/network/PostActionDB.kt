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
    private val currentUserUid by lazy { FirebaseAuth.getInstance().currentUser?.uid }
    private val postRef = db.collection(POST_REF)

    suspend fun addNewPost(post: Post, image: ByteArray) {
        postRef.document(currentUserUid!!).collection(currentUserUid!!).add(post)
            .addOnSuccessListener { document ->
                val postId = document.id
                storage.child("$POST_REF/$currentUserUid/$postId").putBytes(image)
                    .addOnSuccessListener {
                        it.storage.downloadUrl.addOnSuccessListener { uri ->
                            addImageUrl(postId, uri.toString())
                        }
                    }
            }.await()
    }

    private fun addImageUrl(postId: String, imageUrl: String) {
        postRef.document(currentUserUid!!).collection(currentUserUid!!).document(postId)
            .update(POST_IMAGE_URL, imageUrl)
    }
}