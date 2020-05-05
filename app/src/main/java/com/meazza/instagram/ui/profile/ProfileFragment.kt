package com.meazza.instagram.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.meazza.instagram.R
import com.meazza.instagram.databinding.FragmentProfileBinding
import com.meazza.instagram.ui.profile.adapter.ViewPagerProfileAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val profileViewModel by inject<ProfileViewModel>()
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val instagrammer = arguments?.let { ProfileFragmentArgs.fromBundle(it).user }
        uid = instagrammer?.id.toString()

        profileViewModel.run {
            instagramUser.value = instagrammer
            name.value = instagrammer?.name
            username.value = instagrammer?.username
            photoUrl.value = instagrammer?.photoUrl
            bio.value = instagrammer?.bio
            website.value = instagrammer?.website
            postsNumber.value = instagrammer?.postNumber.toString()
            followersNumber.value = instagrammer?.followersNumber.toString()
            followingNumber.value = instagrammer?.followingNumber.toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        profileViewModel.run {
            checkIfCurrentUserIsFollowing(uid).observe(viewLifecycleOwner, Observer {
                val document = it?.exists()
                isCurrentUserFollowing.value = document == true
            })
        }

        return super.onCreateView(inflater, container, savedInstanceState)
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
