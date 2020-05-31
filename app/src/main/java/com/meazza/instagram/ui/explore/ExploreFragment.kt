package com.meazza.instagram.ui.explore

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.meazza.instagram.R
import com.meazza.instagram.common.customization.SpannedGridLayoutManager
import com.meazza.instagram.common.listener.OnPostClickListener
import com.meazza.instagram.data.model.Post
import com.meazza.instagram.databinding.FragmentExploreBinding
import com.meazza.instagram.di.prefs
import com.meazza.instagram.ui.post.adapter.PostAdapter
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

@ExperimentalCoroutinesApi
class ExploreFragment : Fragment(R.layout.fragment_explore), OnPostClickListener {

    private val exploreViewModel by inject<ExploreViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentExploreBinding>(view)?.apply {
            lifecycleOwner = this@ExploreFragment
            viewModel = exploreViewModel
        }

        setUiAction()
        setRecyclerViewAdapter()
        setRecyclerViewLayoutManager()
    }

    private fun setRecyclerViewAdapter() {

        val id = prefs.currentUid

        exploreViewModel.run {
            getPostQuery(id!!).observe(viewLifecycleOwner, Observer {
                val query = it

                val config = PagedList.Config.Builder()
                    .setInitialLoadSizeHint(9)
                    .setPageSize(6)
                    .build()

                val options = FirestorePagingOptions.Builder<Post>()
                    .setLifecycleOwner(viewLifecycleOwner)
                    .setQuery(query, config, Post::class.java)
                    .build()

                adapter.value = PostAdapter(options, this@ExploreFragment)
            })
        }
    }

    private fun setRecyclerViewLayoutManager() {

        val spannedManager = SpannedGridLayoutManager(
            object : SpannedGridLayoutManager.GridSpanLookup {
                override fun getSpanInfo(position: Int): SpannedGridLayoutManager.SpanInfo {
                    return if (position % 12 == 0 || position % 12 == 7) {
                        SpannedGridLayoutManager.SpanInfo(2, 2)
                    } else {
                        SpannedGridLayoutManager.SpanInfo(1, 1)
                    }
                }
            }, 3, 1f
        )

        rv_explore.layoutManager = spannedManager
    }

    private fun setUiAction() {
        tb_explore.setOnClickListener {
            findNavController().navigate(R.id.destination_search)
        }
    }

    override fun onClickPost(post: Post) {
        val action = ExploreFragmentDirections.actionGlobalPostDetail(post)
        findNavController().navigate(action)
    }
}
