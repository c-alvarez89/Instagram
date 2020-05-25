package com.meazza.instagram.util

import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.meazza.instagram.R
import java.util.regex.Pattern

fun isValidEmail(email: String): Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    val patternPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{6,}\$"
    val pattern = Pattern.compile(patternPassword)
    return pattern.matcher(password).matches()
}

fun setToolbar(activity: AppCompatActivity, toolbar: Toolbar, toolbarTitle: String) {
    activity.apply {
        setSupportActionBar(toolbar)
        title = toolbarTitle
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_left)
        }
    }
}