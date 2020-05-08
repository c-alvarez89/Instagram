package com.meazza.instagram.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.meazza.instagram.data.model.DirectMessage
import com.meazza.instagram.data.model.User
import com.meazza.instagram.util.DIRECT_MESSAGE_REF
import com.meazza.instagram.util.SENT_AT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
object MessagingDB {

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val currentUserUid by lazy { FirebaseAuth.getInstance().currentUser?.uid!! }

    private val directMessageRef = db.collection(DIRECT_MESSAGE_REF)

    suspend fun sendMessage(message: DirectMessage) {
        directMessageRef.add(message).await()
    }

    suspend fun subscribeToChat(): Flow<MutableList<DirectMessage>> = callbackFlow {

        val subscription = directMessageRef
            .orderBy(SENT_AT, Query.Direction.DESCENDING)
            .addSnapshotListener { querySnapshot, _ ->
                querySnapshot?.let {
                    val messages = it.toObjects(DirectMessage::class.java)
                    offer(messages)
                }
            }

        awaitClose { subscription.remove() }
    }

    suspend fun getConversations(): Flow<MutableList<User>> = callbackFlow {
        directMessageRef.document(currentUserUid)
            .collection("anotherUserId").get().addOnSuccessListener {
                offer(it.toObjects(User::class.java))
            }.await()
    }
}