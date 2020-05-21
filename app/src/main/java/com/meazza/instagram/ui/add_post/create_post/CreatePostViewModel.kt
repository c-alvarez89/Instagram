package com.meazza.instagram.ui.add_post.create_post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meazza.instagram.data.model.Post
import com.meazza.instagram.data.network.CurrentUserDB
import com.meazza.instagram.data.network.PostActionDB
import kotlinx.coroutines.launch

class CreatePostViewModel(
    private val postDb: PostActionDB,
    private val currentUserDB: CurrentUserDB
) : ViewModel() {

    var caption = MutableLiveData<String>()

    private val uid = MutableLiveData<String>()
    private val username = MutableLiveData<String>()
    private val photoUrl = MutableLiveData<String>()

    fun addImagePost(image: ByteArray) {
        viewModelScope.launch {
            val userUid = uid.value
            val username = username.value
            val photoUrl = photoUrl.value
            val caption = caption.value
            val newPost = Post(userUid, username, photoUrl, caption)
            postDb.createPost(newPost, image)
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            val currentUser = currentUserDB.getUser()
            uid.value = currentUser?.id
            username.value = currentUser?.username
            photoUrl.value = currentUser?.photoUrl
        }
    }
}
