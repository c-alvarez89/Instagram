package com.meazza.instagram.ui.direct_message.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.meazza.instagram.data.model.DirectMessage
import com.meazza.instagram.data.network.CurrentUserDB
import com.meazza.instagram.data.network.MessagingService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class ChatViewModel(
    private val repository: MessagingService,
    private val userInstance: CurrentUserDB
) :
    ViewModel() {

    private val userId by lazy { FirebaseAuth.getInstance().currentUser?.uid.toString() }

    val adapter = ChatAdapter(userId)
    var message = MutableLiveData<String>()
    var photoUrl = MutableLiveData<String>()

    init {
        if (photoUrl.value.isNullOrEmpty()) photoUrl.value = ""
    }

    fun sendMessage() {

        val messageText = message.value
        val photoUrl = photoUrl.value
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
                    repository.sendMessage(directMessage)
                    message.value = ""
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun fetchMessages() = liveData(Dispatchers.IO) {
        repository.subscribeToChat().collect {
            emit(it)
        }
    }

    fun setAdapter(messages: MutableList<DirectMessage>) = adapter.run {
        setListData(messages.asReversed())
        notifyDataSetChanged()
    }

    fun getUser() = viewModelScope.launch {
        val user = userInstance.getUser()
        photoUrl.value = user?.photoUrl
    }
}
