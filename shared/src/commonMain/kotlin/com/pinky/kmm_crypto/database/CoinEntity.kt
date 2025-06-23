package com.pinky.kmm_crypto.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoinEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val amount: Double,
    val coinId: String
)