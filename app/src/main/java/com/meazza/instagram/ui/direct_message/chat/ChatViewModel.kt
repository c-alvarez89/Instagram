package com.meazza.instagram.ui.direct_message.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.meazza.instagram.data.model.DirectMessage
import com.meazza.instagram.data.network.MessagingDB
import com.meazza.instagram.ui.direct_message.adapter.ChatAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class ChatViewModel(private val repository: MessagingDB) : ViewModel() {

    private val currentUser by lazy { FirebaseAuth.getInstance().currentUser }
    private val userId = currentUser?.uid.toString()

    val adapter = ChatAdapter(userId)
    val instagrammerId = MutableLiveData<String>()
    val instagrammerName = MutableLiveData<String>()
    val instagrammerUsername = MutableLiveData<String>()
    val instagrammerPhoto = MutableLiveData<String>()
    var message = MutableLiveData<String>()

    fun sendMessage() {

        val instagrammerId = instagrammerId.value
        val messageText = message.value
        val photoUrl = currentUser?.photoUrl.toString()
        val date = Date()

        viewModelScope.launch {
            messageText?.let {
                if (it.isNotEmpty()) {
                    val directMessage = DirectMessage(
                        userId,
                        messageText,
                        photoUrl,
                        date
                    )
                    instagrammerId?.let { id -> repository.saveMessage(id, directMessage) }
                    message.value = ""
                }
            }
        }
    }

    fun fetchMessages(instagrammerId: String) = liveData(Dispatchers.IO) {
        repository.subscribeToChat(instagrammerId).collect {
            emit(it)
        }
    }

    fun setAdapter(messages: MutableList<DirectMessage>) = adapter.run {
        setListData(messages.asReversed())
        notifyDataSetChanged()
    }
}
