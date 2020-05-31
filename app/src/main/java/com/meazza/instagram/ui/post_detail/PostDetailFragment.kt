package com.meazza.instagram.ui.post_detail

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.meazza.instagram.R
import com.meazza.instagram.data.model.Post
import com.meazza.instagram.databinding.FragmentPostDetailBinding
import com.meazza.instagram.util.setToolbar
import kotlinx.android.synthetic.main.fragment_post_detail.*
import org.koin.android.ext.android.inject


class PostDetailFragment : Fragment(R.layout.fragment_post_detail) {

    private val postDetailViewModel by inject<PostDetailViewModel>()

    private var post: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        post = arguments?.let { PostDetailFragmentArgs.fromBundle(it).post }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentPostDetailBinding>(view)?.apply {
            lifecycleOwner = this@PostDetailFragment
            viewModel = postDetailViewModel
        }

        setPostInfo()
        setToolbar(activity, tb_post_detail, getString(R.string.posts))
    }

    private fun setPostInfo() {
        postDetailViewModel.run {
            userPhotoUrl.value = post?.userPhotoUrl
            username.value = post?.username
            postImageUrl.value = post?.postImageUrl
            likes.value = post?.likesNumber
            caption.value = post?.caption
            comments.value = post?.commentsNumber
            timeAgo.value = post?.publicationDate
        }
    }
}
