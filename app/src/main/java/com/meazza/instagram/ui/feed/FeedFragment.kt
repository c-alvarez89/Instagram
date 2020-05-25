package com.meazza.instagram.ui.feed

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.meazza.instagram.R
import com.meazza.instagram.util.setToolbarWithLeftIcon
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkCurrentUser(view)
        setHasOptionsMenu(true)
        setToolbarWithLeftIcon(activity, tb_feed, "", R.drawable.ic_camera)
    }

    private fun checkCurrentUser(view: View) {
        if (mAuth.currentUser == null) {
            view.findNavController().navigate(
                R.id.action_global_welcome, null,
                NavOptions.Builder().setPopUpTo(R.id.nav_main, true).build()
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_feed, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigate(R.id.goto_camera)
                return true
            }
            R.id.mn_messages -> {
                findNavController().navigate(R.id.goto_messages)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
