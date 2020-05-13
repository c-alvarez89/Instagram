package com.meazza.instagram.common

import com.meazza.instagram.data.model.User


interface UserListener {
    fun onItemClickListener(user: User)
}