package com.meazza.instagram.di

import android.app.Application
import com.meazza.instagram.common.preference.Preferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class App : Application() {

    companion object {
        lateinit var preferences: Preferences
    }

    override fun onCreate() {
        super.onCreate()

        preferences = Preferences(applicationContext)

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    authModule,
                    userModule,
                    feedModule,
                    profileModule,
                    postModule,
                    searchModule,
                    directMessageModule
                )
            )
        }
    }
}
