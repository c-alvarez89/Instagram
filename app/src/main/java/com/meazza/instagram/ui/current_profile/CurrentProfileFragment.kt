package com.meazza.instagram.ui.current_profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.meazza.instagram.R
import com.meazza.instagram.common.adapter.InstagramViewPagerAdapter
import com.meazza.instagram.data.network.AuthService
import com.meazza.instagram.databinding.FragmentCurrentProfileBinding
import com.meazza.instagram.di.prefs
import com.meazza.instagram.ui.post.PostsFragment
import com.meazza.instagram.ui.post.tagged.TaggedPostsFragment
import com.meazza.instagram.ui.profile.ProfileFragmentDirections
import com.meazza.instagram.util.setToolbar
import kotlinx.android.synthetic.main.fragment_current_profile.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

@ExperimentalCoroutinesApi
class CurrentProfileFragment : Fragment(R.layout.fragment_current_profile) {

    private val currentProfileViewModel by inject<CurrentProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentCurrentProfileBinding>(view)?.apply {
            lifecycleOwner = this@CurrentProfileFragment
            viewModel = currentProfileViewModel
        }

        setUiInfo()
        setUiAction()
        setTabLayout()
        setHasOptionsMenu(true)
        setToolbar(activity, tb_current_profile)
    }

    private fun setUiInfo() {
        currentProfileViewModel.run {
            name.value = prefs.name
            username.value = prefs.username
            photoUrl.value = prefs.photoUrl
            bio.value = prefs.bio
            website.value = prefs.website
            postsNumber.value = prefs.postsNumber.toString()
            followersNumber.value = prefs.followersNumber.toString()
            followingNumber.value = prefs.followingNumber.toString()
        }
    }

    private fun setTabLayout() {

        pager_current_profile.adapter =
            InstagramViewPagerAdapter(this, PostsFragment(), TaggedPostsFragment())

        TabLayoutMediator(
            tab_layout_current_profile,
            pager_current_profile,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.setIcon(R.drawable.selector_grid)
                    1 -> tab.setIcon(R.drawable.selector_tagged_user)
                }
            }).attach()
    }

    private fun setUiAction() {
        btn_edit_profile.setOnClickListener {
            findNavController().navigate(R.id.destination_edit_profile)
        }

        ll_followers_current_profile.setOnClickListener {
            gotoFollowers()
        }

        ll_following_current_profile.setOnClickListener {
            gotoFollowers()
        }
    }

    private fun gotoFollowers() {
        val direction = ProfileFragmentDirections.gotoFollowers("")
        findNavController().navigate(direction)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mn_settings) {
            AuthService.signOut
            findNavController()
                .navigate(
                    R.id.action_global_welcome, null,
                    NavOptions.Builder().setPopUpTo(R.id.nav_main, true).build()
                )
        }
        return super.onOptionsItemSelected(item)
    }
}
