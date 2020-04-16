package com.meazza.instagram.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

object AuthRepository {

    private val mAuth by lazy { FirebaseAuth.getInstance() }

    val signOut = mAuth.signOut()

    suspend fun signUpByEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun loginByEmail(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun resetPassword(email: String) {
        mAuth.sendPasswordResetEmail(email).await()
    }
}