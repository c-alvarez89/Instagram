package com.meazza.instagram.data.network

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.meazza.instagram.data.model.User
import com.meazza.instagram.util.*
import kotlinx.coroutines.tasks.await

object CurrentUserDB {

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val storage by lazy { FirebaseStorage.getInstance().reference }
    private val currentUserUid by lazy { FirebaseAuth.getInstance().currentUser?.uid!! }

    private val usersRef = db.collection(USER_REF)

    val currentUserDocRef = usersRef.document(currentUserUid)

    suspend fun createUser(user: User) {
        user.id?.let { usersRef.document(it).set(user).await() }
    }

    suspend fun getUser(): User? {
        val snapshot = usersRef.document(currentUserUid).get().await()
        return snapshot.toObject(User::class.java)
    }

    suspend fun updateUser(name: String, username: String, bio: String, website: String) {
        usersRef.document(currentUserUid).update(
            NAME, name,
            USERNAME, username,
            BIO, bio,
            WEBSITE, website
        ).await()
    }

    suspend fun uploadPhoto(imageUri: Uri) {
        storage.child("$PROFILE_PHOTO_REF/$currentUserUid.jpeg").putFile(imageUri)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                    val photoUrl = downloadUri.toString()
                    updatePhoto(photoUrl)
                }
            }.await()
    }


    private fun updatePhoto(photoUrl: String) {
        usersRef.document(currentUserUid).update(PHOTO_URL, photoUrl)
    }
}
