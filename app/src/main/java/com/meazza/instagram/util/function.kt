package com.meazza.instagram.util

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.util.Patterns
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
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

fun setToolbar(activity: FragmentActivity?, toolbar: View) {
    val mActivity = activity as AppCompatActivity
    mActivity.apply {
        setSupportActionBar(toolbar as Toolbar)
        title = ""
    }
}

fun setToolbar(activity: FragmentActivity?, toolbar: View, toolbarTitle: String) {
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

fun setToolbar(
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

fun toggleLike(empty: ImageView, full: ImageView) {

    val animatorSet = AnimatorSet()

    if (full.visibility == View.VISIBLE) {

        full.scaleX = 0.1f
        full.scaleY = 0.1f

        val scaleDownX = ObjectAnimator.ofFloat(full, "scaleX", 1f, 0f)
        scaleDownX.duration = 300
        scaleDownX.interpolator = AccelerateInterpolator()

        val scaleDownY = ObjectAnimator.ofFloat(full, "scaleY", 1f, 0f)
        scaleDownY.duration = 300
        scaleDownY.interpolator = AccelerateInterpolator()

        full.visibility = View.GONE
        empty.visibility = View.VISIBLE

        animatorSet.playTogether(scaleDownX, scaleDownY)

    } else if (full.visibility == View.GONE) {

        full.scaleX = 0.1f
        full.scaleY = 0.1f

        val scaleDownX = ObjectAnimator.ofFloat(full, "scaleX", 0.1f, 1f)
        scaleDownX.duration = 300
        scaleDownX.interpolator = DecelerateInterpolator()

        val scaleDownY = ObjectAnimator.ofFloat(full, "scaleY", 0.1f, 1f)
        scaleDownY.duration = 300
        scaleDownY.interpolator = DecelerateInterpolator()

        full.visibility = View.VISIBLE
        empty.visibility = View.GONE

        animatorSet.playTogether(scaleDownX, scaleDownY)
    }

    /*if (empty.visibility == View.VISIBLE) {

        empty.scaleX = 0.1f
        empty.scaleY = 0.1f

        val scaleDownX = ObjectAnimator.ofFloat(full, "scaleX", 1f, 0f)
        scaleDownX.duration = 300
        scaleDownX.interpolator = AccelerateInterpolator()

        val scaleDownY = ObjectAnimator.ofFloat(full, "scaleY", 1f, 0f)
        scaleDownY.duration = 300
        scaleDownY.interpolator = AccelerateInterpolator()

        full.visibility = View.GONE
        empty.visibility = View.VISIBLE

        animatorSet.playTogether(scaleDownX, scaleDownY)

    } else if (empty.visibility == View.GONE) {

        empty.scaleX = 0.1f
        empty.scaleY = 0.1f

        val scaleDownX = ObjectAnimator.ofFloat(full, "scaleX", 0.1f, 1f)
        scaleDownX.duration = 300
        scaleDownX.interpolator = DecelerateInterpolator()

        val scaleDownY = ObjectAnimator.ofFloat(full, "scaleY", 0.1f, 1f)
        scaleDownY.duration = 300
        scaleDownY.interpolator = DecelerateInterpolator()

        full.visibility = View.VISIBLE
        empty.visibility = View.GONE

        animatorSet.playTogether(scaleDownX, scaleDownY)
    }
*/
    animatorSet.start()
}
