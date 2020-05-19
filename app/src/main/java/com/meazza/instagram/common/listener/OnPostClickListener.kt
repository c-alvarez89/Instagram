package com.meazza.instagram.common.listener

import com.meazza.instagram.data.model.Post

interface OnPostClickListener {
    fun onClickPost(post: Post)
}