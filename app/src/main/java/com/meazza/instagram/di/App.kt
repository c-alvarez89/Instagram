package com.meazza.instagram.di

import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    authModule,
                    userModule,
                    searchModule,
                    profileModule,
                    postModule,
                    directMessageModule
                )
            )
        }
    }
}