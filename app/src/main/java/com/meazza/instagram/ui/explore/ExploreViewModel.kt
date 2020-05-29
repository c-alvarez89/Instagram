package com.meazza.instagram.ui.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.meazza.instagram.data.network.PostActionDB
import com.meazza.instagram.ui.post.adapter.PostAdapter

class ExploreViewModel(private val postsDb: PostActionDB) : ViewModel() {

    val adapter = MutableLiveData<PostAdapter>()

    fun getPostQuery() = liveData {
        emit(postsDb.getPostsQuery())
    }
}
