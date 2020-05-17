package com.meazza.instagram.common.listener

import com.meazza.instagram.data.model.User


interface UserListener {
    fun onItemClickListener(user: User)
}