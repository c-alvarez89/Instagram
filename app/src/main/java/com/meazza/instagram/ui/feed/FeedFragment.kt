package com.meazza.instagram.ui.feed

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.meazza.instagram.R
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mAuth.currentUser == null) {
            view.findNavController().navigate(
                R.id.action_global_welcome, null,
                NavOptions.Builder().setPopUpTo(R.id.nav_main, true).build()
            )
        }

        setHasOptionsMenu(true)
        setToolbar()
        setUiAction()
    }

    private fun setToolbar() {
        val mActivity = activity as AppCompatActivity
        mActivity.apply {
            setSupportActionBar(tb_feed)
            title = ""
        }
    }

    private fun setUiAction() {
        iv_camera.setOnClickListener {
            findNavController().navigate(R.id.goto_camera)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_feed, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mn_messages)
            findNavController().navigate(R.id.goto_messages)
        return super.onOptionsItemSelected(item)
    }
}
