package com.pinky.kmm_crypto.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.abs

@Serializable
data class GlobalData(
    val data: MarketDataModel
)

@Serializable
data class MarketDataModel(
    @SerialName("total_market_cap")
    val totalMarketCap: PercentageModel,
    @SerialName("total_volume")
    val totalVolume: PercentageModel,
    @SerialName("market_cap_percentage")
    val marketCapPercentage: PercentageModel,
    @SerialName("market_cap_change_percentage_24h_usd")
    val marketCapChangePercentage24hUsd: Double
) {
    val marketCap: Double
        get() = (totalMarketCap.usd ?: 0.0)

    val volume: Double
        get() = (totalVolume.usd ?: 0.0)

    val btcDominance: Double
        get() = (marketCapPercentage.btc ?: 0.0)
}

@Serializable
data class PercentageModel(
    val btc: Double? = null,
    val usd: Double? = null,
)