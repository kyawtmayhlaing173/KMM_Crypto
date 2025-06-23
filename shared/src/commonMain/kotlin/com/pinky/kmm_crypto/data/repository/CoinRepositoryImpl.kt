package com.pinky.kmm_crypto.data.repository

import androidx.sqlite.SQLiteException
import com.pinky.kmm_crypto.data.remote.CoinModel
import com.pinky.kmm_crypto.data.remote.RemoteDataSource
import com.pinky.kmm_crypto.database.CoinEntity
import com.pinky.kmm_crypto.database.PortfolioCoinDao
import com.pinky.kmm_crypto.domain.repository.CoinRepository
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow

internal class CoinRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val portfolioCoinDao: PortfolioCoinDao
): CoinRepository {
    override suspend fun getCoins(): List<CoinModel> {
        val results = remoteDataSource.getCoins()
        println("🔥 $results")
        return results
    }

    override suspend fun getPortfolioCoins(): Flow<List<CoinEntity>> {
        val portfolioCoin = portfolioCoinDao.getPortfolioCoins()
        return portfolioCoin
    }

    override suspend fun savePortfolioCoin(coin: CoinEntity): EmptyResult<DataError.Local> {
        return try {
            portfolioCoinDao.upsertCoin(coin = coin)
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }
}