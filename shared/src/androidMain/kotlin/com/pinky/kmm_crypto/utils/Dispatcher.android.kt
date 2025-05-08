package com.pinky.kmm_crypto.utils

import io.ktor.client.engine.android.Android
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class AndroidDispatcher: Dispatcher {
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
}
internal actual fun provideDispatcher(): Dispatcher = AndroidDispatcher()