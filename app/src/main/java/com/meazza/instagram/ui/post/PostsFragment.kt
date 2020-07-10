package com.meazza.instagram.ui.post

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.meazza.instagram.R
import com.meazza.instagram.common.listener.OnPostClickListener
import com.meazza.instagram.data.model.Post
import com.meazza.instagram.databinding.FragmentPostsBinding
import com.meazza.instagram.ui.post.adapter.PostAdapter
import com.meazza.instagram.util.prefs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject


@ExperimentalCoroutinesApi
class PostsFragment : Fragment(R.layout.fragment_posts), OnPostClickListener {

    private val postsViewModel by inject<PostsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentPostsBinding>(view)?.apply {
            lifecycleOwner = this@PostsFragment
            viewModel = postsViewModel
        }

        setRecyclerViewAdapter()
    }

    private fun setRecyclerViewAdapter() {

        val id = prefs.instagrammerId

        postsViewModel.run {
            getPostQuery(id!!).observe(viewLifecycleOwner, Observer { query ->

                val config = PagedList.Config.Builder()
                    .setInitialLoadSizeHint(9)
                    .setPageSize(6)
                    .build()

                val options = FirestorePagingOptions.Builder<Post>()
                    .setLifecycleOwner(viewLifecycleOwner)
                    .setQuery(query, config, Post::class.java)
                    .build()

                adapter.value = PostAdapter(options, this@PostsFragment)
            })
        }
    }

    override fun onClickPost(post: Post) {
        val action = PostsFragmentDirections.actionGlobalPostDetail(post)
        findNavController().navigate(action)
    }
}
