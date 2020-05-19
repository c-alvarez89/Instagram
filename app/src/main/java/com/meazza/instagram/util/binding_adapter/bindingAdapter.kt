package com.meazza.instagram.util.binding_adapter

import android.view.View
import android.widget.Button
import android.widget.SearchView
import androidx.databinding.BindingAdapter


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