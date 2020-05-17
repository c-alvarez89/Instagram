package com.meazza.instagram.ui.add_post.share

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meazza.instagram.data.model.Post
import com.meazza.instagram.data.network.PostActionDB
import kotlinx.coroutines.launch

class NewPostViewModel(private val postDb: PostActionDB) : ViewModel() {

    var caption = MutableLiveData<String>()

    fun addImagePost(image: ByteArray) {
        viewModelScope.launch {
            val newPost = Post(caption.value!!)
            postDb.addNewPost(newPost, image)
        }
    }
}
