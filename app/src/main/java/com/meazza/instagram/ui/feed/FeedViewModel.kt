package com.meazza.instagram.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.meazza.instagram.data.network.CurrentUserDB

class FeedViewModel(private val currentUserDb: CurrentUserDB) : ViewModel() {

    fun getCurrentUser() = liveData {
        emit(currentUserDb.getUser())
    }
}
