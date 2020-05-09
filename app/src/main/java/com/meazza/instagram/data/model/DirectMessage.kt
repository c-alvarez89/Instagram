package com.meazza.instagram.data.model

import com.google.firebase.firestore.DocumentReference
import java.util.*

data class DirectMessage(
    val authorId: String = "",
    val message: String? = "",
    var userPhotoRef: DocumentReference? = null,
    val sentAt: Date = Date()
)