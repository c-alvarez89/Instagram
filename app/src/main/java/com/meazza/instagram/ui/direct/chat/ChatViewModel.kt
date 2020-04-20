package com.meazza.instagram.ui.direct.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.meazza.instagram.repository.DatabaseRepository
import com.meazza.instagram.vo.DirectMessage
import kotlinx.coroutines.launch
import java.util.*

class ChatViewModel(private val repository: DatabaseRepository) : ViewModel() {

    private val user = repository.user
    private val userId = user!!.uid

    var message = MutableLiveData<String>()
    val getAdapter = ChatAdapter(userId)

    fun sendMessage() {

        val messageText = message.toString()
        val photoUrl = ""
        val date = Date()

        viewModelScope.launch {

            if (messageText.isNotEmpty()) {
                val directMessage = DirectMessage(userId, messageText, photoUrl, date)
                repository.sendMessage(directMessage)
                message.value = ""
            }
        }
    }

    fun fetchMessages() = liveData {
        val mutableData: MutableList<DirectMessage>? = null
        emit(mutableData)
    }

    fun setAdapter(messages: MutableList<DirectMessage>) = getAdapter.run {
        setListData(messages)
        notifyDataSetChanged()
    }
}
