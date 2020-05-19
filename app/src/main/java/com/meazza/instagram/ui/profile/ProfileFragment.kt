package com.meazza.instagram.ui.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.meazza.instagram.R
import com.meazza.instagram.data.model.User
import com.meazza.instagram.data.network.AuthService
import com.meazza.instagram.databinding.FragmentProfileBinding
import com.meazza.instagram.ui.profile.adapter.ProfileViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val profileViewModel by inject<ProfileViewModel>()

    private var user = MutableLiveData<User>()
    private var instagrammerUid: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentProfileBinding>(view)?.apply {
            lifecycleOwner = this@ProfileFragment
            viewModel = profileViewModel
        }

        user.value = arguments?.let { ProfileFragmentArgs.fromBundle(it).user }


        profileViewModel.run {
            instagrammerUid?.let {
                checkIfCurrentUserIsFollowing(it).observe(viewLifecycleOwner, Observer { document ->
                    val existsDocument = document?.exists()
                    isCurrentUserFollowing.value = existsDocument == true
                })
            }

            getCurrentUser().observe(viewLifecycleOwner, Observer { instagrammer ->
                user.value = instagrammer
                name.value = instagrammer?.name
                username.value = instagrammer?.username
                photoUrl.value = instagrammer?.photoUrl
                bio.value = instagrammer?.bio
                website.value = instagrammer?.website
                postsNumber.value = instagrammer?.postNumber.toString()
                followersNumber.value = instagrammer?.followersNumber.toString()
                followingNumber.value = instagrammer?.followingNumber.toString()
            })
        }

        setHasOptionsMenu(true)
        setToolbar()
        setTabLayout()
        setUiAction()
    }

    private fun setToolbar() {
        val mActivity = activity as AppCompatActivity
        mActivity.apply {
            setSupportActionBar(tb_profile)
            title = ""
            supportActionBar?.run {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_arrow_left)
            }
        }
    }

    private fun setUiAction() {
        /* btn_send_message.setOnClickListener {
             val direction = instagrammer?.let { user -> ProfileFragmentDirections.gotoChat(user) }
             direction?.let { action -> findNavController().navigate(action) }
         }*/
    }

    private fun setTabLayout() {
        pager_profile.adapter = ProfileViewPagerAdapter(this)

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
