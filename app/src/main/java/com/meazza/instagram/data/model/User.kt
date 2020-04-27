package com.meazza.instagram.data.model

import com.google.firebase.firestore.Exclude

data class User(
    @get:Exclude
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val username: String = "",
    val phoneNumber: String = "",
    val photoUrl: String = "",
    val bio: String = "",
    val website: String = "",
    val postNumber: Int = 0,
    val followersNumber: Int = 0,
    val followingNumber: Int = 0
)