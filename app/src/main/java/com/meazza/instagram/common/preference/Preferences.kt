package com.meazza.instagram.common.preference

import android.content.Context

class Preferences(context: Context) {

    companion object {
        private const val FILE_NAME = "instagram_prefs"
    }

    private val prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    var instagrammerId: String?
        get() = prefs.getString("instagrammerId", "")
        set(value) = prefs.edit().putString("instagrammerId", value).apply()
}