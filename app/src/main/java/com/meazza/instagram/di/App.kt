package com.meazza.instagram.di

import android.app.Application
import com.meazza.instagram.common.preference.Preferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class App : Application() {

    companion object {
        var prefs: Preferences? = null
    }

    override fun onCreate() {
        super.onCreate()

        prefs = Preferences(applicationContext)

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    authModule,
                    userModule,
                    feedModule,
                    searchModule,
                    profileModule,
                    postModule,
                    directMessageModule
                )
            )
        }
    }
}

@ExperimentalCoroutinesApi
val preferences by lazy { App.prefs!! }
