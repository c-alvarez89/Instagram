package com.meazza.instagram.data.model

import java.util.*

data class Post(
    val caption: String = "",
    val postImageUrl: String = "",
    val publicationDate: Date = Date(),
    val likesNumber: Int = 0,
    val commentsNumber: Int = 0
)