package com.meazza.instagram.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.meazza.instagram.data.model.DirectMessage
import com.meazza.instagram.data.model.User
import com.meazza.instagram.util.*
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
    private val conversationRef = db.collection(CONVERSATION_REF)
    private val usersRef = db.collection(USER_REF)

    suspend fun saveMessage(instagrammer: User, message: DirectMessage) {

        val instagramUserDocRef = usersRef.document(instagrammer.id!!)
        val currentUserDocRef = usersRef.document(currentUserUid)

        directMessageRef.document(currentUserUid).collection(instagrammer.id).add(message).await()
        directMessageRef.document(instagrammer.id).collection(currentUserUid).add(message).await()

        conversationRef.document(currentUserUid).collection(currentUserUid)
            .document(instagrammer.id)
            .set(hashMapOf(DOC_REF to instagramUserDocRef)).await()

        conversationRef.document(instagrammer.id).collection(instagrammer.id)
            .document(currentUserUid)
            .set(hashMapOf(DOC_REF to currentUserDocRef)).await()
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

    suspend fun getConversations(): Flow<MutableList<User>> = callbackFlow {

        val userList = mutableListOf<User>()
        val query = conversationRef.document(currentUserUid)
            .collection(currentUserUid).get().await()

        for (document in query) {
            val snapshot = document.getDocumentReference(DOC_REF)?.get()?.await()
            val user = snapshot?.toObject(User::class.java)
            user?.let { userList.add(it) }
        }

        offer(userList)
    }
}