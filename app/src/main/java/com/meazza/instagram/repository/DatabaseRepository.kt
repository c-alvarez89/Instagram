package com.meazza.instagram.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.meazza.instagram.vo.DirectMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

object DatabaseRepository {

    val user = AuthRepository.currentUser

    private const val USER_REF = "user"
    private const val CHAT_REF = "chat"
    private const val ORDER_BY = "sentAt"

    private val db by lazy { FirebaseFirestore.getInstance() }

    suspend fun sendMessage(message: DirectMessage) {
        val nMessage = db.collection(CHAT_REF).add(message).await()
        val id = nMessage.id
    }

    @ExperimentalCoroutinesApi
    suspend fun subscribeToChat(): Flow<MutableList<DirectMessage>> = callbackFlow {

        val eventDocument = db.collection(CHAT_REF)

        val suscription =
            eventDocument.orderBy(ORDER_BY, Query.Direction.DESCENDING)
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    querySnapshot?.let {
                        val messages = it.toObjects(DirectMessage::class.java)
                        offer(messages)
                    }
                }

        awaitClose { suscription.remove() }
    }
}