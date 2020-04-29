package com.meazza.instagram.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import com.meazza.instagram.R

@BindingAdapter("setImage")
fun loadImage(imageView: ImageView, url: String) {
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