package com.meazza.instagram.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.meazza.instagram.ui.auth.AuthHostActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class MainEmptyActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (mAuth.currentUser == null) {
            startActivity(intentFor<AuthHostActivity>().newTask().clearTask())
        } else {
            startActivity(intentFor<MainHostActivity>().newTask().clearTask())
        }
    }
}
