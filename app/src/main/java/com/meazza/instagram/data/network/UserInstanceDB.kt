package com.meazza.instagram.data.network

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.meazza.instagram.data.model.User
import kotlinx.coroutines.tasks.await

object UserInstanceDB {

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val storage by lazy { FirebaseStorage.getInstance().reference }
    private val userUid by lazy { FirebaseAuth.getInstance().currentUser?.uid }

    private const val USER_REF = "users"
    private const val PROFILE_PHOTO_REF = "profile_photos"

    private val usersRef: CollectionReference = db.collection(USER_REF)

    lateinit var downloadUri: Uri

    suspend fun createUser(user: User) {
        usersRef.document(user.id).set(user).await()
    }

    suspend fun getUser(): User? {
        val snapshot = usersRef.document(userUid!!).get().await()
        return snapshot.toObject(User::class.java)
    }

    suspend fun uploadImage(userPhoto: Uri) {
        storage.child("$PROFILE_PHOTO_REF/$userUid").putFile(userPhoto).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener { uri ->
                downloadUri = uri
            }
        }.await()
    }
}

