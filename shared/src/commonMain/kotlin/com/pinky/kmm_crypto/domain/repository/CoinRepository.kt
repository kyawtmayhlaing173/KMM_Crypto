package com.pinky.kmm_crypto.domain.repository

import com.pinky.kmm_crypto.data.remote.CoinModel

internal interface CoinRepository {
    suspend fun getCoins(): List<CoinModel>
}