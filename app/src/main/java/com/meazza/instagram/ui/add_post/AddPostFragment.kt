package com.meazza.instagram.ui.add_post

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.meazza.instagram.R
import com.meazza.instagram.common.permission.PermissionRequest
import com.meazza.instagram.common.permission.PermissionState
import com.meazza.instagram.ui.add_post.adapter.AddPostViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_add_post.*

class AddPostFragment : Fragment(R.layout.fragment_add_post) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions()
        setTabLayout()
    }

    private fun checkPermissions() {
        when (PermissionRequest(activity as Activity)
            .allPermissions()) {
            PermissionState.ALL_PERMISSIONS_GRANTED -> {
            }
            PermissionState.DENIED -> {
            }
            PermissionState.PERMANENTLY_DENIED -> {
            }
        }
    }

    private fun setTabLayout() {
        pager_add_post.adapter = AddPostViewPagerAdapter(this)

        TabLayoutMediator(
            tab_layout_add_post,
            pager_add_post,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.gallery)
                    1 -> tab.text = getString(R.string.photo)
                }
            }).attach()
    }
}
