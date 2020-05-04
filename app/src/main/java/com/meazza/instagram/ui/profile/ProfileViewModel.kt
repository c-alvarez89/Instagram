package com.meazza.instagram.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.meazza.instagram.data.model.User
import com.meazza.instagram.data.network.FollowActionDB
import kotlinx.coroutines.launch

class ProfileViewModel(private val followActionDb: FollowActionDB) : ViewModel() {

    val instagramUser = MutableLiveData<User>()
    val name = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val bio = MutableLiveData<String>()
    val photoUrl = MutableLiveData<String>()
    val website = MutableLiveData<String>()
    val postsNumber = MutableLiveData<String>()
    val followersNumber = MutableLiveData<String>()
    val followingNumber = MutableLiveData<String>()
    val isCurrentUserFollowing = MutableLiveData<Boolean>()

    fun checkIfCurrentUserIsFollowing(instagrammerUid: String?) = liveData {
        emit(followActionDb.checkIfCurrentUserIsFollowing(instagrammerUid))
    }

    fun saveFollow() = viewModelScope.launch {
        val instagrammer = instagramUser.value
        followActionDb.saveFollow(instagrammer!!)
        isCurrentUserFollowing.value = true
    }

    fun stopFollowing() = viewModelScope.launch {
        val instagrammer = instagramUser.value
        followActionDb.stopFollowing(instagrammer!!)
        isCurrentUserFollowing.value = false
    }
}

