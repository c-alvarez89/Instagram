package com.meazza.instagram.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.meazza.instagram.data.network.CurrentUserDB

class FeedViewModel(private val currentUserDB: CurrentUserDB) : ViewModel() {

    fun getCurrentUser() = liveData {
        emit(currentUserDB.getUser())
    }
}
