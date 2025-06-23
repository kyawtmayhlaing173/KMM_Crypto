package com.pinky.kmm_crypto.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PortfolioCoinDao {
    @Upsert
    suspend fun upsertCoin(coin: CoinEntity)

    @Query("SELECT * FROM CoinEntity")
    fun getPortfolioCoins(): Flow<List<CoinEntity>>
}