package com.meazza.instagram.ui.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.meazza.instagram.R
import com.meazza.instagram.databinding.FragmentProfileBinding
import com.meazza.instagram.ui.profile.adapter.ViewPagerProfileAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val profileViewModel by inject<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = arguments?.let { ProfileFragmentArgs.fromBundle(it).user }
        profileViewModel.run {
            name.value = user?.name
            username.value = user?.username
            photoUrl.value = user?.photoUrl
            bio.value = user?.bio
            website.value = user?.website
            postsNumber.value = user?.postNumber.toString()
            followersNumber.value = user?.followersNumber.toString()
            followingNumber.value = user?.followingNumber.toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentProfileBinding>(view)?.apply {
            lifecycleOwner = this@ProfileFragment
            viewModel = profileViewModel
        }

        setHasOptionsMenu(true)
        setToolbar()
        setTabLayout()
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

    private fun setTabLayout() {
        pager_profile.adapter = ViewPagerProfileAdapter(this)

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
}
