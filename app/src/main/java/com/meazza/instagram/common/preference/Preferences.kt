package com.meazza.instagram.common.preference

import android.content.Context
import com.meazza.instagram.util.*

class Preferences(context: Context) {

    companion object {
        private const val FILE_NAME = "instagram_prefs"
    }

    private val prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    var name: String?
        get() = prefs.getString(NAME, "")
        set(value) = prefs.edit().putString(NAME, value).apply()

    var username: String?
        get() = prefs.getString(USERNAME, "")
        set(value) = prefs.edit().putString(USERNAME, value).apply()

    var photoUrl: String?
        get() = prefs.getString(PHOTO_URL, "")
        set(value) = prefs.edit().putString(PHOTO_URL, value).apply()

    var bio: String?
        get() = prefs.getString(BIO, "")
        set(value) = prefs.edit().putString(BIO, value).apply()

    var website: String?
        get() = prefs.getString(WEBSITE, "")
        set(value) = prefs.edit().putString(WEBSITE, value).apply()

    var postsNumber: Int
        get() = prefs.getInt(POSTS_NUMBER, 0)
        set(value) = prefs.edit().putInt(POSTS_NUMBER, value).apply()

    var followersNumber: Int
        get() = prefs.getInt(FOLLOWERS_NUMBER, 0)
        set(value) = prefs.edit().putInt(FOLLOWERS_NUMBER, value).apply()

    var followingNumber: Int
        get() = prefs.getInt(FOLLOWING_NUMBER, 0)
        set(value) = prefs.edit().putInt(FOLLOWING_NUMBER, value).apply()

    var instagrammerId: String?
        get() = prefs.getString("instagrammerId", "")
        set(value) = prefs.edit().putString("instagrammerId", value).apply()
}