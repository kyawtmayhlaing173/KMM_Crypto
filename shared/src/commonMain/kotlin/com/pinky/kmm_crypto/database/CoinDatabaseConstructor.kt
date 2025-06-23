package com.pinky.kmm_crypto.database

import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object CoinDatabaseConstructor: RoomDatabaseConstructor<PortfolioCoinDatabase> {
    override fun initialize(): PortfolioCoinDatabase
}