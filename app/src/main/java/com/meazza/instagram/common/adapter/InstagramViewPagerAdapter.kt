package com.meazza.instagram.common.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class InstagramViewPagerAdapter(
    container: Fragment,
    private val firstFragment: Fragment,
    private val secondFragment: Fragment
) : FragmentStateAdapter(container) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> firstFragment
            else -> secondFragment
        }
    }
}