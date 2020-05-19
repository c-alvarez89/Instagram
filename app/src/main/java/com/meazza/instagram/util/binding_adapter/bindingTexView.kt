package com.meazza.instagram.util.binding_adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

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