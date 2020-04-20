package com.meazza.instagram.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.meazza.instagram.vo.DirectMessage
import kotlinx.coroutines.tasks.await

object DatabaseRepository {

    val user = AuthRepository.currentUser

    private const val CHAT_REF = "chat"

    private val db by lazy { FirebaseFirestore.getInstance() }

    suspend fun sendMessage(message: DirectMessage) {
        db.collection(CHAT_REF).add(message).await()
    }

    fun subscribeToChat() {

        val messagesList : ArrayList<DirectMessage> = ArrayList()

        db.collection(CHAT_REF).addSnapshotListener { snapShot, _ ->
            messagesList.clear()
            val messages = snapShot?.toObjects(DirectMessage::class.java)
//            messagesList.addAll(messages)
        }
    }
}