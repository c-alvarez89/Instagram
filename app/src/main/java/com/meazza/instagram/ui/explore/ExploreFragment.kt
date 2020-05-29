package com.meazza.instagram.ui.explore

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.meazza.instagram.R
import com.meazza.instagram.common.decoration.SpannedGridLayoutManager
import com.meazza.instagram.databinding.FragmentExploreBinding
import kotlinx.android.synthetic.main.fragment_explore.*
import org.koin.android.ext.android.inject

class ExploreFragment : Fragment(R.layout.fragment_explore) {

    private val exploreViewModel by inject<ExploreViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentExploreBinding>(view)?.apply {
            lifecycleOwner = this@ExploreFragment
            viewModel = exploreViewModel
        }

        exploreViewModel.configRecyclerView(viewLifecycleOwner)

        setUiAction()
        setLayoutManager()
    }

    private fun setLayoutManager() {

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
}
