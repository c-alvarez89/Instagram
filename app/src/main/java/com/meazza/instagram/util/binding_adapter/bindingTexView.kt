package com.meazza.instagram.util.binding_adapter

import android.annotation.SuppressLint
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.TextView
import androidx.core.text.bold
import androidx.databinding.BindingAdapter
import com.meazza.instagram.R
import java.text.SimpleDateFormat
import java.util.*

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

@BindingAdapter("setTextLikes")
fun setTextLikes(textView: TextView, likes: Int) {
    when (likes) {
        0 -> textView.visibility = View.GONE
        1 -> textView.text = textView.resources.getString(R.string.like)
        else -> textView.text = textView.resources.getString(R.string.likes, likes.toString())
    }
}

@BindingAdapter("setCaption", "setUsername")
fun setCaption(textView: TextView, caption: String, username: String) {
    if (caption == "") {
        textView.visibility = View.GONE
    } else {
        textView.text = SpannableStringBuilder().bold { append("$username ") }.append(caption)
    }
}

@BindingAdapter("setTextComments")
fun setTextComments(textView: TextView, comments: Int) {
    when (comments) {
        0 -> textView.visibility = View.GONE
        1 -> textView.text = textView.resources.getString(R.string.view_comment)
        else -> textView.text =
            textView.resources.getString(R.string.view_all_comments, comments.toString())
    }
}

@BindingAdapter("setFormatDate")
fun setFormatDate(textView: TextView, timeAgo: Date) {
    val pattern = "MMMM d"
    val dateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
    textView.text = dateFormat.format(timeAgo)
}
