package com.meazza.instagram.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.meazza.instagram.data.model.DirectMessage
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
    private val currentUser by lazy { FirebaseAuth.getInstance().currentUser }

    private val currentUserUid = currentUser?.uid.toString()

    private val directMessageRef = db.collection(DIRECT_MESSAGE_REF)

    suspend fun saveMessage(instagrammerId: String, message: DirectMessage) {
        db.collection(currentUserUid).document(instagrammerId).collection(instagrammerId)
            .add(message).await()
        db.collection(instagrammerId).document(currentUserUid).collection(currentUserUid)
            .add(message).await()
    }

    suspend fun subscribeToChat(instagrammerId: String): Flow<MutableList<DirectMessage>> =
        callbackFlow {

            val subscription = directMessageRef.document(currentUserUid)
                .collection(instagrammerId)
                .orderBy(SENT_AT, Query.Direction.DESCENDING)
                .addSnapshotListener { querySnapshot, _ ->
                    querySnapshot?.let {
                        val messages = it.toObjects(DirectMessage::class.java)
                        offer(messages)
                    }
                }

            awaitClose { subscription.remove() }
        }

    suspend fun getConversations() {
        db.collection(currentUserUid).get().await()
    }
}