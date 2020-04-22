package com.meazza.instagram.ui.main.profile.edit_profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.meazza.instagram.R
import kotlinx.android.synthetic.main.fragment_edit_profile.*

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setToolbar()
    }

    private fun setToolbar() {
        val activity = activity as AppCompatActivity
        activity.apply {
            setSupportActionBar(tb_edit_profile)
            title = ""
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
