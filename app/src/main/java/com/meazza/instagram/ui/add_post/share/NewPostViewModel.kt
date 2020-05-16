package com.meazza.instagram.ui.add_post.share

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meazza.instagram.data.model.Post
import com.meazza.instagram.data.network.PostActionDB
import kotlinx.coroutines.launch

class NewPostViewModel(private val postDb: PostActionDB) : ViewModel() {

    fun addImagePost(post: Post, image: ByteArray) {
        viewModelScope.launch {
            postDb.addNewPost(post, image)
        }
    }
}
