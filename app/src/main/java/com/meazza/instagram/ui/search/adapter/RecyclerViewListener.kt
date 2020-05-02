package com.meazza.instagram.ui.search.adapter

import com.meazza.instagram.data.model.User

interface RecyclerViewListener {
    fun onItemClickListener(user: User)
}