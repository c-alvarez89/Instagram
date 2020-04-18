package com.meazza.instagram.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.meazza.instagram.R
import kotlinx.android.synthetic.main.activity_main_host.*

class MainHostActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.nav_main_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_host)

        NavigationUI.setupWithNavController(bottom_nav, navController)
        setBottomNavVisibility()
    }

    private fun setBottomNavVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.destination_camera -> bottom_nav.visibility = View.GONE
                R.id.destination_direct -> bottom_nav.visibility = View.GONE
                R.id.destination_chat -> bottom_nav.visibility = View.GONE
                R.id.destination_edit_profile -> bottom_nav.visibility = View.GONE
                else -> bottom_nav.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
