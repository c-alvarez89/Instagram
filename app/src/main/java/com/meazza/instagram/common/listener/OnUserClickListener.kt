package com.meazza.instagram.common.listener

import com.meazza.instagram.data.model.User


interface OnUserClickListener {
    fun onItemClickListener(user: User)
}