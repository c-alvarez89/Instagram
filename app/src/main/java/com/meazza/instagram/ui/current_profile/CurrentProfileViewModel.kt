package com.meazza.instagram.ui.current_profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentProfileViewModel : ViewModel() {

    val name = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val bio = MutableLiveData<String>()
    val photoUrl = MutableLiveData<String>()
    val website = MutableLiveData<String>()
    val postsNumber = MutableLiveData<String>()
    val followersNumber = MutableLiveData<String>()
    val followingNumber = MutableLiveData<String>()
}
