package com.pinky.kmm_crypto.domain.repository

import com.pinky.kmm_crypto.data.remote.CoinModel
import com.pinky.kmm_crypto.database.CoinEntity
import com.pinky.kmm_crypto.domain.model.PortfolioCoin
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import kotlinx.coroutines.flow.Flow

internal interface CoinRepository {
    suspend fun getCoins(): List<CoinModel>
    suspend fun getPortfolioCoins(): Flow<List<CoinEntity>>
    suspend fun savePortfolioCoin(coin: CoinEntity): EmptyResult<DataError.Local>
}