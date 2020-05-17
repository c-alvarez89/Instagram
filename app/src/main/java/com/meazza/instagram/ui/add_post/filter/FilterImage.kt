package com.meazza.instagram.ui.add_post.filter

import android.os.Parcel
import android.os.Parcelable

data class FilterImage(val image: String? = "", val filter: String? = "Normal") : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(filter)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FilterImage> {
        override fun createFromParcel(parcel: Parcel): FilterImage {
            return FilterImage(parcel)
        }

        override fun newArray(size: Int): Array<FilterImage?> {
            return arrayOfNulls(size)
        }
    }
}

