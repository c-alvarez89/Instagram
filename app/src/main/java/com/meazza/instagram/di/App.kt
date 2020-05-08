package com.meazza.instagram.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(authModule, userModule, searchModule, profileModule, directMessageModule))
        }
    }
}