package com.meazza.instagram.data.network

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

object AuthService {

    private val mAuth by lazy { FirebaseAuth.getInstance() }
    val signOut by lazy { mAuth.signOut() }

    lateinit var userUid: String

    suspend fun signUpByEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            userUid = it.user!!.uid
        }.await()
    }

    suspend fun loginByEmail(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun resetPassword(email: String) {
        mAuth.sendPasswordResetEmail(email).await()
    }
}