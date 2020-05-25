package com.meazza.instagram.util

import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
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

fun setToolbarWithBackArrow(activity: FragmentActivity?, toolbar: View, toolbarTitle: String) {
    val mActivity = activity as AppCompatActivity
    mActivity.apply {
        setSupportActionBar(toolbar as Toolbar)
        title = toolbarTitle
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_left)
        }
    }
}

fun setToolbarWithLeftIcon(
    activity: FragmentActivity?,
    toolbar: View,
    toolbarTitle: String,
    icon: Int
) {
    val mActivity = activity as AppCompatActivity
    mActivity.apply {
        setSupportActionBar(toolbar as Toolbar)
        title = toolbarTitle
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(icon)
        }
    }
}
