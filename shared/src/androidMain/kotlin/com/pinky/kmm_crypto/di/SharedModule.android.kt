package com.pinky.kmm_crypto.di

import com.pinky.kmm_crypto.database.DatabaseFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { DatabaseFactory(androidContext()) }
    }