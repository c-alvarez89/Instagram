package com.meazza.instagram.data.network

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.meazza.instagram.data.model.User
import com.meazza.instagram.util.USERNAME
import com.meazza.instagram.util.USER_REF
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


object RequestDB {

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val usersRef: CollectionReference = db.collection(USER_REF)

    @ExperimentalCoroutinesApi
    suspend fun requestUsers(text: String?): Flow<MutableList<User>> = callbackFlow {
        if (!text.isNullOrEmpty()) {
            usersRef.orderBy(USERNAME)
                .startAt(text)
                .endAt("$text\uf8ff")
                .get().addOnSuccessListener {
                    val user = it.toObjects(User::class.java)
                    offer(user)
                }.await()
        }
    }
}