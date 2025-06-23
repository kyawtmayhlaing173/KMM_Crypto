package com.pinky.kmm_crypto

import android.app.Application
import com.pinky.kmm_crypto.di.appModule
import com.pinky.kmm_crypto.di.getSharedModules
import com.pinky.kmm_crypto.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Crypto: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Crypto)
            modules(appModule + getSharedModules() + platformModule)
        }
    }
}