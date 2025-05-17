package com.pinky.kmm_crypto.domain.repository

import com.pinky.kmm_crypto.data.remote.GlobalData

internal interface MarketDataRepository {
    suspend fun getMarketData(): GlobalData
}