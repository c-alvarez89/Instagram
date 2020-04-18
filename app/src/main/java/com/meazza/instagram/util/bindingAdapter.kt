package com.meazza.instagram.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation

@BindingAdapter("image")
fun loadImage(imageView: ImageView, url: String) {
    imageView.load(url) {
        crossfade(true)
        transformations(CircleCropTransformation())
    }
}