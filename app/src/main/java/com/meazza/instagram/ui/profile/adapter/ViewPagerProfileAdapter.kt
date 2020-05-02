package com.meazza.instagram.ui.profile.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.meazza.instagram.ui.profile.post.PostsFragment
import com.meazza.instagram.ui.profile.post.TaggedPostsFragment

class ViewPagerProfileAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PostsFragment()
            else -> TaggedPostsFragment()
        }
    }
}