package com.meazza.instagram.ui.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.meazza.instagram.R
import com.meazza.instagram.common.adapter.InstagramViewPagerAdapter
import com.meazza.instagram.data.model.User
import com.meazza.instagram.data.network.AuthService
import com.meazza.instagram.databinding.FragmentProfileBinding
import com.meazza.instagram.di.preferences
import com.meazza.instagram.ui.post.PostsFragment
import com.meazza.instagram.ui.post.tagged.TaggedPostsFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

@ExperimentalCoroutinesApi
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val profileViewModel by inject<ProfileViewModel>()
    private val currentUserUid by lazy { FirebaseAuth.getInstance().currentUser?.uid }

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            arguments.takeIf { true }?.run {
                user = ProfileFragmentArgs.fromBundle(this).user
                if (user?.id == currentUserUid) user = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentProfileBinding>(view)?.apply {
            lifecycleOwner = this@ProfileFragment
            viewModel = profileViewModel
        }

        when {
            user == null -> setCurrentUser()
            user != null -> setInstagrammer()
        }

        setToolbar()
        setTabLayout()
        setUiAction()
    }

    private fun setToolbar() {
        setHasOptionsMenu(true)
        val mActivity = activity as AppCompatActivity
        mActivity.apply {
            setSupportActionBar(tb_profile)
            title = ""
            supportActionBar?.run {
                if (profileViewModel.isCurrentUser.value == false) {
                    setDisplayHomeAsUpEnabled(true)
                    setHomeAsUpIndicator(R.drawable.ic_arrow_left)
                }
            }
        }
    }

    private fun setTabLayout() {

        val posts = PostsFragment()
        val tagged = TaggedPostsFragment()

        pager_profile.adapter = InstagramViewPagerAdapter(this, posts, tagged)
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
            val direction = ProfileFragmentDirections.gotoChat(user!!)
            findNavController().navigate(direction)
        }

        ll_followers_profile.setOnClickListener {
            val direction = ProfileFragmentDirections.gotoFollowers(user!!)
            findNavController().navigate(direction)
        }

        ll_following_profile.setOnClickListener {
            val direction = ProfileFragmentDirections.gotoFollowers(user!!)
            findNavController().navigate(direction)
        }
    }

    private fun setCurrentUser() {
        profileViewModel.run {
            isCurrentUser.value = true
            getCurrentUser().observe(viewLifecycleOwner, Observer { currentUser ->
                user = currentUser
                setUserInfo(currentUser)
                preferences.instagrammerId = user?.id
            })
        }
    }

    private fun setInstagrammer() {
        setUserInfo(user)
        profileViewModel.run {
            isCurrentUser.value = false
            checkIfCurrentUserIsFollowing(user?.id!!).observe(viewLifecycleOwner, Observer {
                val existsDocument = it?.exists()
                isCurrentUserFollowing.value = existsDocument == true
            })
        }
    }

    private fun setUserInfo(user: User?) {
        profileViewModel.run {
            instagrammer.value = user
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (profileViewModel.isCurrentUser.value == true) inflater.inflate(
            R.menu.menu_profile,
            menu
        )
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
