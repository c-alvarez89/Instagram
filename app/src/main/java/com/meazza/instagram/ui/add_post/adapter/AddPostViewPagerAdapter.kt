package com.meazza.instagram.ui.add_post.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.meazza.instagram.ui.add_post.from.GalleryFragment
import com.meazza.instagram.ui.add_post.from.PhotoFragment

class AddPostViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GalleryFragment()
            else -> PhotoFragment()
        }
    }

    override fun getItemCount(): Int = 2
}