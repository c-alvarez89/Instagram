package com.meazza.instagram.ui.direct.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.meazza.instagram.repository.DatabaseRepository
import com.meazza.instagram.vo.DirectMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class ChatViewModel(private val repository: DatabaseRepository) : ViewModel() {

    private val user = repository.user
    private val userId = user!!.uid

    var message = MutableLiveData<String>()
    val getAdapter = ChatAdapter(userId)

    fun sendMessage() {

        val messageText = message.value
        val photoUrl = ""
        val date = Date()

        viewModelScope.launch {
            messageText?.let {
                if (it.isNotEmpty()) {
                    val directMessage = DirectMessage(userId, messageText, photoUrl, date)
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

    fun setAdapter(messages: MutableList<DirectMessage>) = getAdapter.run {
        setListData(messages.asReversed())
        notifyDataSetChanged()
    }
}
