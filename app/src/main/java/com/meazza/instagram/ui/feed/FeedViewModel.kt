package com.meazza.instagram.ui.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.meazza.instagram.data.network.CurrentUserDB
import com.meazza.instagram.data.network.PostActionDB
import com.meazza.instagram.ui.post_detail.adapter.PostDetailAdapter

class FeedViewModel(
    private val currentUserDb: CurrentUserDB,
    private val postsDb: PostActionDB
) : ViewModel() {

    val adapter = MutableLiveData<PostDetailAdapter>()

    fun getCurrentUser() = liveData {
        emit(currentUserDb.getUser())
    }

    fun getPostsQuery(id: String) = liveData {
        emit(postsDb.getPostsQuery(id))
    }
}
