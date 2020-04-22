package com.meazza.instagram.vo

import java.util.*

data class DirectMessage(
    val authorId: String = "",
    val message: String? = "",
    val userImageUrl: String = "",
    val sentAt: Date = Date()
)