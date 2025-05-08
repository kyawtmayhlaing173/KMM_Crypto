package com.pinky.kmm_crypto.data.repository

import com.pinky.kmm_crypto.data.remote.CoinModel
import com.pinky.kmm_crypto.data.remote.RemoteDataSource
import com.pinky.kmm_crypto.domain.repository.CoinRepository

internal class CoinRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): CoinRepository {
    override suspend fun getCoins(): List<CoinModel> {
        val results = remoteDataSource.getCoins()
        println("🔥 $results")
        return results
    }
}