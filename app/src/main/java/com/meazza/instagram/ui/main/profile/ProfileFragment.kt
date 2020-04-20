package com.meazza.instagram.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.meazza.instagram.R
import com.meazza.instagram.ui.main.profile.post_profile.ViewPagerProfileAdapter
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTabLayout()
    }

    private fun setTabLayout() {
        pager_profile.adapter = ViewPagerProfileAdapter(this)
        TabLayoutMediator(
            tab_layout,
            pager_profile,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.setIcon(R.drawable.ic_grid)
                    1 -> tab.setIcon(R.drawable.ic_tagged_user)
                }
            }).attach()
    }
}
