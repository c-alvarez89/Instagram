package com.meazza.instagram.ui.profile

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.meazza.instagram.R
import com.meazza.instagram.common.adapter.InstagramViewPagerAdapter
import com.meazza.instagram.data.model.User
import com.meazza.instagram.databinding.FragmentProfileBinding
import com.meazza.instagram.ui.post.PostsFragment
import com.meazza.instagram.ui.post.tagged.TaggedPostsFragment
import com.meazza.instagram.util.prefs
import com.meazza.instagram.util.setToolbar
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

@ExperimentalCoroutinesApi
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val profileViewModel by inject<ProfileViewModel>()

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = ProfileFragmentArgs.fromBundle(it).user
            prefs.instagrammerId = user.id
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentProfileBinding>(view)?.apply {
            lifecycleOwner = this@ProfileFragment
            viewModel = profileViewModel
        }

        setUiInfo()
        setUiAction()
        setTabLayout()
        setToolbar(activity, tb_profile, "")
    }

    private fun setUiInfo() {
        profileViewModel.run {

            instagrammer.value = user
            name.value = user.name
            username.value = user.username
            photoUrl.value = user.photoUrl
            bio.value = user.bio
            website.value = user.website
            postsNumber.value = user.postNumber.toString()
            followersNumber.value = user.followersNumber.toString()
            followingNumber.value = user.followingNumber.toString()

            checkIfCurrentUserIsFollowing(user.id!!).observe(viewLifecycleOwner, Observer {
                val existsDocument = it?.exists()
                isCurrentUserFollowing.value = existsDocument == true
            })
        }
    }

    private fun setTabLayout() {

        pager_profile.adapter =
            InstagramViewPagerAdapter(this, PostsFragment(), TaggedPostsFragment())

        TabLayoutMediator(
            tab_layout_profile,
            pager_profile,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.setIcon(R.drawable.selector_grid)
                    1 -> tab.setIcon(R.drawable.selector_tagged_user)
                }
            }).attach()
    }

    private fun setUiAction() {

        btn_send_message.setOnClickListener {
            val direction = ProfileFragmentDirections.actionChat(user)
            findNavController().navigate(direction)
        }

        ll_followers_profile.setOnClickListener {
            gotoFollowers()
        }

        ll_following_profile.setOnClickListener {
            gotoFollowers()
        }
    }

    private fun gotoFollowers() {
        val direction = ProfileFragmentDirections.actionFollowers(user)
        findNavController().navigate(direction)
    }
}
