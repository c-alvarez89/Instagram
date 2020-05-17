package com.meazza.instagram.ui.profile.adapter

import com.meazza.instagram.data.model.Post

interface PostListener {
    fun onClickPost(post: Post)
}