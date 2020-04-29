package com.meazza.instagram.ui.user_profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meazza.instagram.common.OnItemClickListener
import com.meazza.instagram.data.network.UserInstanceDB
import kotlinx.coroutines.launch

class ProfileViewModel(private val userInstance: UserInstanceDB) : ViewModel() {

    var onClickListener: OnItemClickListener? = null

    var name = MutableLiveData<String>()
    var username = MutableLiveData<String>()
    var photoUrl = MutableLiveData<String>()
    var bio = MutableLiveData<String>()
    var website = MutableLiveData<String>()
    var posts = MutableLiveData<String>()
    var followers = MutableLiveData<String>()
    var following = MutableLiveData<String>()

    init {
        if (photoUrl.value.isNullOrEmpty()) photoUrl.value = ""
        if (name.value.isNullOrEmpty()) name.value = ""
        if (username.value.isNullOrEmpty()) username.value = ""
        if (bio.value.isNullOrEmpty()) bio.value = ""
        if (website.value.isNullOrEmpty()) website.value = ""
    }

    fun getUser() = viewModelScope.launch {
        val user = userInstance.getUser()
        name.value = user?.name
        username.value = user?.username
        photoUrl.value = user?.photoUrl
        bio.value = user?.bio
        website.value = user?.website
        posts.value = user?.postNumber.toString()
        followers.value = user?.followersNumber.toString()
        following.value = user?.followingNumber.toString()
    }

    fun changeUserPhoto() = viewModelScope.launch {
        onClickListener?.onClickFromViewModel()
    }

    fun uploadImage(userPhoto: ByteArray) = viewModelScope.launch {
        userInstance.uploadImage(userPhoto)
    }

    fun saveChanges() = viewModelScope.launch {
        userInstance.updateUser(
            name.value!!,
            username.value!!,
            bio.value!!,
            website.value!!
        )
    }
}
