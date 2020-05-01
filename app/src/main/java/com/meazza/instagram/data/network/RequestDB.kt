package com.meazza.instagram.data.network

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.meazza.instagram.data.model.User
import com.meazza.instagram.util.USERNAME
import com.meazza.instagram.util.USER_REF
import kotlinx.coroutines.tasks.await


object RequestDB {

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val usersRef: CollectionReference = db.collection(USER_REF)

    suspend fun requestUsers(text: String?): MutableList<User> {
        var searchList = mutableListOf<User>()
        if (!text.isNullOrEmpty()) {
            usersRef.orderBy(USERNAME)
                .startAt(text)
                .endAt("$text\uf8ff")
                .get().addOnSuccessListener {
                    searchList = it.toObjects(User::class.java)
                }.await()
        }
        return searchList
    }
}