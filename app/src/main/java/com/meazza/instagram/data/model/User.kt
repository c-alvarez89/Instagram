package com.meazza.instagram.data.model

import android.os.Parcel
import android.os.Parcelable

data class User(
    val id: String? = "",
    val name: String? = "",
    val email: String? = "",
    val username: String? = "",
    val phoneNumber: String? = "",
    val photoUrl: String? = "",
    val bio: String? = "",
    val website: String? = "",
    val postNumber: Int = 0,
    val followersNumber: Int = 0,
    val followingNumber: Int = 0
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(username)
        parcel.writeString(phoneNumber)
        parcel.writeString(photoUrl)
        parcel.writeString(bio)
        parcel.writeString(website)
        parcel.writeInt(postNumber)
        parcel.writeInt(followersNumber)
        parcel.writeInt(followingNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}