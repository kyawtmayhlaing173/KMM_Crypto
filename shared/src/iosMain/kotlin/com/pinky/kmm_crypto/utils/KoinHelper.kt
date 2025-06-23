package com.pinky.kmm_crypto.utils

import com.pinky.kmm_crypto.di.getSharedModules
import com.pinky.kmm_crypto.di.platformModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(getSharedModules() + platformModule)
    }
}