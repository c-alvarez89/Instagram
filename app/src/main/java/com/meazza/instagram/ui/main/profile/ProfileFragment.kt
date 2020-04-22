package com.meazza.instagram.ui.main.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.meazza.instagram.R
import com.meazza.instagram.repository.AuthRepository
import com.meazza.instagram.ui.auth.AuthHostActivity
import com.meazza.instagram.ui.main.profile.post_profile.ViewPagerProfileAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.newTask
import org.jetbrains.anko.support.v4.intentFor

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setTabLayout()
        setUiAction()
        setToolbar()
    }

    private fun setToolbar() {
        val mActivity = activity as AppCompatActivity
        mActivity.apply {
            setSupportActionBar(tb_profile)
        }
    }

    private fun setTabLayout() {
        pager_profile.adapter = ViewPagerProfileAdapter(this)
        TabLayoutMediator(
            tab_layout,
            pager_profile,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.setIcon(R.drawable.ic_grid)
                    1 -> tab.setIcon(R.drawable.ic_tagged_user)
                }
            }).attach()
    }

    private fun setUiAction() {
        btn_edit_user_profile.setOnClickListener {
            findNavController().navigate(R.id.goto_edit_profile)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mn_settings) {
            AuthRepository.signOut
            startActivity(intentFor<AuthHostActivity>().newTask().clearTask())
        }
        return super.onOptionsItemSelected(item)
    }
}
