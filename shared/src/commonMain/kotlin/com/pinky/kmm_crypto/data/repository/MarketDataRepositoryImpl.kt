package com.pinky.kmm_crypto.data.repository

import com.pinky.kmm_crypto.data.remote.GlobalData
import com.pinky.kmm_crypto.data.remote.RemoteDataSource
import com.pinky.kmm_crypto.domain.repository.MarketDataRepository

internal class MarketDataRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): MarketDataRepository {
    override suspend fun getMarketData(): GlobalData {
        val results = remoteDataSource.getMarketData()
        println("Results: $results")
        return results
    }
}