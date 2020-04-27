package com.meazza.instagram.data.network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.meazza.instagram.data.model.DirectMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

object MessagingService {

    private const val CHAT_REF = "chat"
    private const val ORDER_BY = "sentAt"

    private val db by lazy { FirebaseFirestore.getInstance() }

    suspend fun sendMessage(message: DirectMessage) {
        db.collection(CHAT_REF).add(message).await()
    }

    @ExperimentalCoroutinesApi
    suspend fun subscribeToChat(): Flow<MutableList<DirectMessage>> = callbackFlow {

        val eventDocument = db.collection(CHAT_REF)

        val subscription = eventDocument
            .orderBy(ORDER_BY, Query.Direction.DESCENDING)
            .addSnapshotListener { querySnapshot, _ ->
                querySnapshot?.let {
                    val messages = it.toObjects(DirectMessage::class.java)
                    offer(messages)
                }
            }

        awaitClose { subscription.remove() }
    }
}