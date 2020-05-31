package com.meazza.instagram.util.binding_adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.google.firebase.firestore.DocumentReference
import com.meazza.instagram.R
import com.meazza.instagram.data.model.User
import com.meazza.instagram.util.circleImage
import com.meazza.instagram.util.factory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@BindingAdapter("setRoundImage")
fun setRoundImage(imageView: ImageView, url: String?) {
    imageView.circleImage(url)
}

@BindingAdapter("setSquareImage")
fun setSquareImage(imageView: ImageView, url: String?) {

    Glide.with(imageView.context)
        .load(url)
        .transition(withCrossFade(factory))
        .centerCrop()
        .override(800, 800)
        .error(R.color.gray_icon)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(imageView)
        .waitForLayout()
}

@BindingAdapter("setUserPhotoChat")
fun setUserPhotoChat(imageView: ImageView, reference: DocumentReference?) {

    CoroutineScope(Dispatchers.Main).launch {

        val document = reference?.get()?.await()
        val user = document?.toObject(User::class.java)
        val url = user?.photoUrl.toString()

        imageView.circleImage(url)
    }
}
