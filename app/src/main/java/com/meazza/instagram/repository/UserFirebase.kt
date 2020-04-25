package com.meazza.instagram.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

object UserFirebase {

    private const val TAG = "UserFirebaseError"

    private val currentUser by lazy { FirebaseAuth.getInstance().currentUser }

    suspend fun updateName(name: String): Boolean {

        var result = false
        val profile = UserProfileChangeRequest
            .Builder()
            .setDisplayName(name)
            .build()

        currentUser?.updateProfile(profile)?.addOnCompleteListener {
            if (it.isSuccessful) result = true
            else Log.e(TAG, "Update Name Error")
        }?.await()

        return result
    }

    suspend fun updatePhoto(photo: Uri): Boolean {

        var result = false

        val profile = UserProfileChangeRequest
            .Builder()
            .setPhotoUri(photo)
            .build()

        currentUser?.updateProfile(profile)?.addOnCompleteListener {
            if (it.isSuccessful) result = true
            else Log.e(TAG, "Update Photo Error")
        }?.await()

        return result
    }
}