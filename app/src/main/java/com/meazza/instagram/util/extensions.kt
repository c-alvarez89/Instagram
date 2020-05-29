package com.meazza.instagram.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcel
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.meazza.instagram.R
import java.util.*

fun Parcel.writeDate(date: Date?) {
    writeLong(date?.time ?: -1)
}

fun Parcel.readDate(): Date? {
    val long = readLong()
    return if (long != -1L) Date(long) else null
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun ImageView.circleImage(uri: Uri?) {
    Glide.with(this.context)
        .load(uri)
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .circleCrop()
        .error(R.color.gray)
        .into(this)
}

fun ImageView.load(uri: Uri?) {
    Glide.with(this.context)
        .load(uri)
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .centerCrop()
        .error(R.color.gray)
        .into(this)
}

fun ImageView.load(bitmap: Bitmap?) {
    Glide.with(this.context)
        .load(bitmap)
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .centerCrop()
        .error(R.color.gray)
        .into(this)
}