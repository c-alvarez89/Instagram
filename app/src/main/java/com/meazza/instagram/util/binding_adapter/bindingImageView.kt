package com.meazza.instagram.util.binding_adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.google.firebase.firestore.DocumentReference
import com.meazza.instagram.R
import com.meazza.instagram.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@BindingAdapter("setRoundImage")
fun setRoundImage(imageView: ImageView, url: String?) {

    if (url == "") {
        imageView.load(R.drawable.ic_user_photo) {
            transformations(CircleCropTransformation())
        }
    } else {
        imageView.load(url) {
            crossfade(100)
            placeholder(R.drawable.ic_user_photo)
            transformations(CircleCropTransformation())
        }
    }
}

@BindingAdapter("setSquareImage")
fun setSquareImage(imageView: ImageView, url: String?) {

    if (url == "") {
        imageView.load(R.color.gray)
    } else {

        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(imageView.context)
            .load(url)
            .transition(withCrossFade(factory))
            .centerCrop()
            .override(800, 800)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imageView)
            .waitForLayout()
    }
}

@BindingAdapter("setUserPhotoChat")
fun setUserPhotoChat(imageView: ImageView, reference: DocumentReference?) {

    CoroutineScope(Dispatchers.IO).launch {

        val document = reference?.get()?.await()
        val user = document?.toObject(User::class.java)
        val url = user?.photoUrl.toString()

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
}
