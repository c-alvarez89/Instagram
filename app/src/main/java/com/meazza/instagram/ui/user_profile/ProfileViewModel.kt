package com.meazza.instagram.ui.user_profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meazza.instagram.data.network.UserInstanceDB
import com.meazza.instagram.listener.UiActionListener
import kotlinx.coroutines.launch

class ProfileViewModel(private val userInstance: UserInstanceDB) : ViewModel() {

    var uiActionListener: UiActionListener? = null

    var name = MutableLiveData<String>()
    var username = MutableLiveData<String>()
    var bio = MutableLiveData<String>()
    var website = MutableLiveData<String>()
    var posts = MutableLiveData<String>()
    var followers = MutableLiveData<String>()
    var following = MutableLiveData<String>()

    var photoUri = MutableLiveData<Uri>()

    fun getUser() = viewModelScope.launch {
        val user = userInstance.getUser()
        name.value = user?.name
        username.value = user?.username
        bio.value = user?.bio
        website.value = user?.website
        posts.value = user?.postNumber.toString()
        followers.value = user?.followersNumber.toString()
        following.value = user?.followingNumber.toString()
    }

    fun uploadImage() = viewModelScope.launch {
        photoUri.value?.let { userInstance.uploadImage(it) }
    }

    fun changeUserPhoto() = viewModelScope.launch {
        uiActionListener?.setUiActionFromViewModel()
    }

}
