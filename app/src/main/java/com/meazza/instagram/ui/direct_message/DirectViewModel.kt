package com.meazza.instagram.ui.direct_message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.meazza.instagram.data.model.User
import com.meazza.instagram.data.network.MessagingDB
import com.meazza.instagram.ui.direct_message.adapter.DirectAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class DirectViewModel(private val database: MessagingDB) : ViewModel() {

    val adapter = MutableLiveData<DirectAdapter>()

    fun getConversations() = liveData {
        database.getConversations().collect {
            emit(it)
        }
    }

    fun setAdapter(users: MutableList<User>) = adapter.value?.run {
        setUserList(users)
        notifyDataSetChanged()
    }
}
