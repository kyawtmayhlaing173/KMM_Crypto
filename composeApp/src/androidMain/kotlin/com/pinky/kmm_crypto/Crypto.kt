package com.pinky.kmm_crypto

import android.app.Application
import com.pinky.kmm_crypto.di.appModule
import com.pinky.kmm_crypto.di.getSharedModules
import org.koin.core.context.startKoin

class Crypto: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule + getSharedModules())
        }
    }
}