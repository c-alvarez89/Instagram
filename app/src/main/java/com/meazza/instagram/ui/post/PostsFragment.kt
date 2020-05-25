package com.meazza.instagram.ui.post

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.meazza.instagram.R
import com.meazza.instagram.common.listener.OnPostClickListener
import com.meazza.instagram.data.model.Post
import com.meazza.instagram.databinding.FragmentPostsBinding
import com.meazza.instagram.ui.post.adapter.PostAdapter
import com.meazza.instagram.ui.profile.ProfileFragmentDirections
import org.koin.android.ext.android.inject


class PostsFragment : Fragment(R.layout.fragment_posts), OnPostClickListener {

    private val postsViewModel by inject<PostsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentPostsBinding>(view)?.apply {
            lifecycleOwner = this@PostsFragment
            viewModel = postsViewModel
        }

        postsViewModel.run {
            adapter.value = PostAdapter(this@PostsFragment)
            getPosts().observe(viewLifecycleOwner, Observer {
                setAdapter(it)
            })
        }
    }

    override fun onClickPost(post: Post) {
        val action = ProfileFragmentDirections.gotoPostDetail(post)
        findNavController().navigate(action)
    }
}
