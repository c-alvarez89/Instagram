package com.meazza.instagram.ui.feed

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.meazza.instagram.R
import com.meazza.instagram.data.model.Post
import com.meazza.instagram.databinding.FragmentFeedBinding
import com.meazza.instagram.ui.post_detail.adapter.PostDetailAdapter
import com.meazza.instagram.util.prefs
import com.meazza.instagram.util.setToolbar
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

@ExperimentalCoroutinesApi
class FeedFragment : Fragment(R.layout.fragment_feed) {

    private val feedViewModel by inject<FeedViewModel>()

    private val mAuth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentFeedBinding>(view)?.apply {
            lifecycleOwner = this@FeedFragment
            viewModel = feedViewModel
        }

        setHasOptionsMenu(true)
        setToolbar(activity, tb_feed, "", R.drawable.ic_camera)
        checkCurrentUser(view)
        setCurrentUserInfo()
        setRecyclerViewAdapter()
    }

    private fun checkCurrentUser(view: View) {
        if (mAuth.currentUser == null) {
            view.findNavController().navigate(
                R.id.action_global_welcome, null,
                NavOptions.Builder().setPopUpTo(R.id.nav_main, true).build()
            )
        }
    }

    private fun setCurrentUserInfo() {
        feedViewModel.getCurrentUser().observe(viewLifecycleOwner, Observer {
            prefs.currentUid = it?.id
            prefs.name = it?.name
            prefs.username = it?.username
            prefs.photoUrl = it?.photoUrl
            prefs.bio = it?.bio
            prefs.website = it?.website
            prefs.postsNumber = it?.postNumber!!
            prefs.followersNumber = it.followersNumber
            prefs.followingNumber = it.followingNumber
        })
    }

    fun setRecyclerViewAdapter() {

        val id = prefs.currentUid

        feedViewModel.run {
            getPostsQuery(id!!).observe(viewLifecycleOwner, Observer { query ->

                val config = PagedList.Config.Builder()
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build()

                val options = FirestorePagingOptions
                    .Builder<Post>()
                    .setLifecycleOwner(viewLifecycleOwner)
                    .setQuery(query, config, Post::class.java)
                    .build()

                adapter.value =
                    PostDetailAdapter(
                        options
                    )
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_feed, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigate(R.id.destination_camera)
                return true
            }
            R.id.mn_messages -> {
                findNavController().navigate(R.id.destination_direct)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
