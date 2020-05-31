package com.meazza.instagram.ui.profile.follower_list.list

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.meazza.instagram.R
import com.meazza.instagram.databinding.FragmentFollowerListBinding
import com.meazza.instagram.ui.profile.follower_list.FollowersViewModel
import org.koin.android.ext.android.inject


class FollowerListFragment : Fragment(R.layout.fragment_follower_list) {

    private val followersViewModel by inject<FollowersViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentFollowerListBinding>(view)?.apply {
            lifecycleOwner = this@FollowerListFragment
            viewModel = followersViewModel
        }
    }
}
