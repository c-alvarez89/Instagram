package com.meazza.instagram.ui.post_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class PostDetailViewModel : ViewModel() {

    val userPhotoUrl = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val postImageUrl = MutableLiveData<String>()
    val likes = MutableLiveData<Int>()
    val caption = MutableLiveData<String>()
    val comments = MutableLiveData<Int>()
    val timeAgo = MutableLiveData<Date>()
}