package com.pinky.kmm_crypto.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<PortfolioCoinDatabase>
}