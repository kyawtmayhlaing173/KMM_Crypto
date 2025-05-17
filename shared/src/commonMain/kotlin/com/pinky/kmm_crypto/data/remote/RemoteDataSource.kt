package com.pinky.kmm_crypto.data.remote

import com.pinky.kmm_crypto.utils.Dispatcher
import kotlinx.coroutines.withContext

internal class RemoteDataSource(
    private val apiService: CoinService,
    private val dispatcher: Dispatcher
) {
    suspend fun getCoins() = withContext(dispatcher.io) {
        apiService.getCoins()
    }

    suspend fun getMarketData() = withContext(dispatcher.io) {
        apiService.getMarketData()
    }
}