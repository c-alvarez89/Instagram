package com.meazza.instagram.data.model

import android.os.Parcel
import android.os.Parcelable
import com.meazza.instagram.util.readDate
import com.meazza.instagram.util.writeDate
import java.util.*

data class Post(
    val userId: String? = "",
    val username: String? = "",
    val userPhotoUrl: String? = "",
    val caption: String? = "",
    val postImageUrl: String? = "",
    val likesNumber: Int = 0,
    val commentsNumber: Int = 0,
    val publicationDate: Date? = Date()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDate()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(username)
        parcel.writeString(userPhotoUrl)
        parcel.writeString(caption)
        parcel.writeString(postImageUrl)
        parcel.writeInt(likesNumber)
        parcel.writeInt(commentsNumber)
        parcel.writeDate(publicationDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}