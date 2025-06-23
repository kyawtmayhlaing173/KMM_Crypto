package com.pinky.kmm_crypto.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CoinEntity::class],
    version = 1
)
@TypeConverters(StringListTypeConverter::class)
abstract class PortfolioCoinDatabase : RoomDatabase() {
    abstract val portfolioCoinDao: PortfolioCoinDao
    companion object {
        const val DATABASE_NAME = "portfolio_coin.db"
    }
}