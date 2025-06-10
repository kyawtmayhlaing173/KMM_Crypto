package com.pinky.kmm_crypto.utils

import com.pinky.kmm_crypto.di.getSharedModules
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(getSharedModules())
    }
}