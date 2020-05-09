package com.meazza.instagram.ui.direct_message.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.meazza.instagram.data.model.DirectMessage
import com.meazza.instagram.data.model.User
import com.meazza.instagram.data.network.CurrentUserDB
import com.meazza.instagram.data.network.MessagingDB
import com.meazza.instagram.ui.direct_message.adapter.ChatAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class ChatViewModel(
    private val database: MessagingDB,
    private val currentUserInstance: CurrentUserDB
) : ViewModel() {

    private val currentUser by lazy { FirebaseAuth.getInstance().currentUser }
    private val userId = currentUser?.uid.toString()

    val adapter = ChatAdapter(userId)
    val instagrammerUser = MutableLiveData<User>()
    val instagrammerName = MutableLiveData<String>()
    val instagrammerUsername = MutableLiveData<String>()
    val instagrammerPhoto = MutableLiveData<String>()
    var message = MutableLiveData<String>()

    fun sendMessage() {

        val instagrammer = instagrammerUser.value
        val messageText = message.value
        val date = Date()
        val photoRef = currentUserInstance.currentUserDocRef

        viewModelScope.launch {
            messageText?.let {
                if (it.isNotEmpty()) {
                    val directMessage = DirectMessage(
                        userId,
                        messageText,
                        photoRef,
                        date
                    )
                    instagrammer?.let { user -> database.saveMessage(user, directMessage) }
                    message.value = ""
                }
            }
        }
    }

    fun fetchMessages(instagrammerId: String) = liveData(Dispatchers.IO) {
        database.subscribeToChat(instagrammerId).collect {
            emit(it)
        }
    }

    fun setAdapter(messages: MutableList<DirectMessage>) = adapter.run {
        setListData(messages.asReversed())
        notifyDataSetChanged()
    }
}
