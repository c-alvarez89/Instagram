package com.meazza.instagram.vo

import java.util.*

data class Message(
    val authorId: String,
    val message: String,
    val sentAt: Date
)