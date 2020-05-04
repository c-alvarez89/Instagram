package com.meazza.instagram.util

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import com.meazza.instagram.R

@BindingAdapter("setImage")
fun loadImage(imageView: ImageView, url: String?) {
    if (url == "") {
        imageView.load(R.drawable.ic_user_photo) {
            transformations(CircleCropTransformation())
        }
    } else {
        imageView.load(url) {
            crossfade(true)
            placeholder(R.drawable.ic_user_photo)
            transformations(CircleCropTransformation())
        }
    }
}

@BindingAdapter("isTextEmpty")
fun textViewVisibility(textView: TextView, text: String?) {
    if (text == "") {
        textView.visibility = View.GONE
    } else {
        textView.visibility = View.VISIBLE
    }
}

@SuppressLint("DefaultLocale")
@BindingAdapter("toLowerCase")
fun toLowerCase(textView: TextView, text: String?) {
    textView.text = text?.toLowerCase() ?: ""
}

@BindingAdapter("setQueryTextListener")
fun setOnQueryTextListener(
    searchView: SearchView,
    listener: SearchView.OnQueryTextListener
) {
    searchView.setOnQueryTextListener(listener)
}

@BindingAdapter("showFollowButton")
fun showFollowButton(button: Button, isFollowing: Boolean) {
    if (!isFollowing) {
        button.visibility = View.VISIBLE
    } else {
        button.visibility = View.GONE
    }
}

@BindingAdapter("showFollowingButton")
fun showFollowingButton(button: Button, isFollowing: Boolean) {
    if (isFollowing) {
        button.visibility = View.VISIBLE
    } else {
        button.visibility = View.GONE
    }
}