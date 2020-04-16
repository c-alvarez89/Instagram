package com.meazza.instagram.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.meazza.instagram.R
import kotlinx.android.synthetic.main.activity_main_host.*

class MainHostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_host)

        val navController = findNavController(R.id.nav_main_host_fragment)
        NavigationUI.setupWithNavController(bottom_nav, navController)
    }
}
