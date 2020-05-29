package com.meazza.instagram.ui.explore

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.meazza.instagram.data.model.Post
import com.meazza.instagram.data.network.PostActionDB
import com.meazza.instagram.ui.explore.adapter.ExploreAdapter

class ExploreViewModel(private val postsDb: PostActionDB) : ViewModel() {

    val adapter = MutableLiveData<ExploreAdapter>()

    fun configRecyclerView(owner: LifecycleOwner) {

        val query = postsDb.getPostsQuery()

        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(9)
            .setPageSize(6)
            .build()

        val options = FirestorePagingOptions.Builder<Post>()
            .setLifecycleOwner(owner)
            .setQuery(query, config, Post::class.java)
            .build()

        adapter.value = ExploreAdapter(options)
    }

    fun setAdapter() = adapter.value?.run {
        notifyDataSetChanged()
    }
}
