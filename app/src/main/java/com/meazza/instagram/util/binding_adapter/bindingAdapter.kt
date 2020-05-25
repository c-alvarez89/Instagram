package com.meazza.instagram.util.binding_adapter

import android.view.View
import android.widget.Button
import android.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meazza.instagram.common.decoration.GridSpacingItemDecoration

@BindingAdapter("showFollowButton")
fun showFollowButton(button: Button, isFollowing: Boolean) {
    if (!isFollowing) {
        button.visibility = View.VISIBLE
    } else {
        button.visibility = View.GONE
    }
}

@BindingAdapter("showEditProfileButton")
fun showEditProfileButton(view: View, isCurrentUser: Boolean) {
    if (isCurrentUser) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("setQueryTextListener")
fun setOnQueryTextListener(searchView: SearchView, listener: SearchView.OnQueryTextListener) {
    searchView.setOnQueryTextListener(listener)
}

@BindingAdapter("itemDecoration")
fun addItemDecoration(recyclerView: RecyclerView, decoration: GridSpacingItemDecoration) {
    recyclerView.addItemDecoration(decoration)
}