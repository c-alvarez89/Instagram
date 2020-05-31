package com.meazza.instagram.ui.add_post

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.meazza.instagram.R
import com.meazza.instagram.common.adapter.InstagramViewPagerAdapter
import com.meazza.instagram.common.permission.PermissionRequest
import com.meazza.instagram.common.permission.PermissionState
import com.meazza.instagram.ui.add_post.from.GalleryFragment
import com.meazza.instagram.ui.add_post.from.PhotoFragment
import kotlinx.android.synthetic.main.container_add_post.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class AddPostContainer : Fragment(R.layout.container_add_post) {

    private val hideHandler = Handler()

    private val hidePart2Runnable = Runnable {
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    private val hideRunnable = Runnable { hideHandler.postDelayed(hidePart2Runnable, 0) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions()
        setTabLayout()
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        hideHandler.removeCallbacks(hideRunnable)
        hideHandler.postDelayed(hideRunnable, 0)
    }

    override fun onPause() {
        super.onPause()
        activity?.window?.run {
            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.window?.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
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

        pager_add_post.adapter = InstagramViewPagerAdapter(this, GalleryFragment(), PhotoFragment())

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
