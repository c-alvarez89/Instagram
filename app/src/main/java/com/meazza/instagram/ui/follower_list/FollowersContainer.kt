package com.meazza.instagram.ui.follower_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.meazza.instagram.R
import com.meazza.instagram.common.adapter.InstagramViewPagerAdapter
import com.meazza.instagram.ui.follower_list.list.FollowerListFragment
import com.meazza.instagram.ui.follower_list.list.FollowingListFragment
import com.meazza.instagram.util.setToolbar
import kotlinx.android.synthetic.main.container_followers.*

class FollowersContainer : Fragment(R.layout.container_followers) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.let { FollowersContainerArgs.fromBundle(it).userId }

        setTabLayout()
        setToolbar(activity, tb_follower, userId!!)
    }

    private fun setTabLayout() {

        pager_followers.adapter =
            InstagramViewPagerAdapter(this, FollowerListFragment(), FollowingListFragment())

        TabLayoutMediator(
            tab_layout_follower,
            pager_followers,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.followers)
                    1 -> tab.text = getString(R.string.following)
                }
            }
        ).attach()
    }
}
