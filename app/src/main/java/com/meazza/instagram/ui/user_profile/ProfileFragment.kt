package com.meazza.instagram.ui.user_profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.meazza.instagram.R
import com.meazza.instagram.databinding.FragmentProfileBinding
import com.meazza.instagram.repository.AuthRepository
import com.meazza.instagram.ui.user_profile.post_profile.ViewPagerProfileAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val profileViewModel by inject<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentProfileBinding>(view)?.apply {
            lifecycleOwner = this@ProfileFragment
            viewModel = profileViewModel
        }

        setHasOptionsMenu(true)
        setTabLayout()
        setUiAction()
        setToolbar()
    }

    private fun setToolbar() {
        val mActivity = activity as AppCompatActivity
        mActivity.apply {
            setSupportActionBar(tb_profile)
            title = ""
        }
    }

    private fun setTabLayout() {
        pager_profile.adapter = ViewPagerProfileAdapter(this)
        TabLayoutMediator(
            tab_layout,
            pager_profile,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.setIcon(R.drawable.selector_grid)
                    1 -> tab.setIcon(R.drawable.selector_tagged_user)
                }
            }).attach()
    }

    private fun setUiAction() {
        btn_edit_user_profile.setOnClickListener {
            findNavController().navigate(R.id.next_action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mn_settings) {
            AuthRepository.signOut
            findNavController()
                .navigate(
                    R.id.action_global_welcome, null,
                    NavOptions.Builder().setPopUpTo(R.id.nav_main, true).build()
                )
        }
        return super.onOptionsItemSelected(item)
    }
}
