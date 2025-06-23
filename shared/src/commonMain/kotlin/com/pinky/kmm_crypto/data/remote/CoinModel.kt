package com.pinky.kmm_crypto.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinModel(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @SerialName("current_price")
    val currentPrice: Double? = 0.0,
    @SerialName("market_cap")
    val marketCap: Double?,
    @SerialName("market_cap_rank")
    val marketCapRank: Double?,
    @SerialName("fully_diluted_valuation")
    val fullyDilutedValuation: Double?,
    @SerialName("total_volume")
    val totalVolume: Double?,
    @SerialName("high_24h")
    val high24h: Double?,
    @SerialName("low_24h")
    val low24h: Double?,
    @SerialName("price_change_24h")
    val priceChange24H: Double?,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24H: Double?,
    @SerialName("market_cap_change_24h")
    val marketCapChange24H: Double?,
    @SerialName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24H: Double?,
    @SerialName("circulating_supply")
    val circulatingSupply: Double?,
    @SerialName("total_supply")
    val totalSupply: Double?,
    @SerialName("max_supply")
    val maxSupply: Double?,
    val ath: Double?,
    @SerialName("ath_change_percentage")
    val athChangePercentage: Double?,
    @SerialName("ath_date")
    val athDate: String?,
    val atl: Double?,
    @SerialName("alt_change_percentage")
    val atlChangePercentage: Double? = null,
    @SerialName("alt_date")
    val atlDate: String? = null,
    @SerialName("last_updated")
    val lastUpdated: String?,
    @SerialName("sparkline_in_7d")
    val sparklineIn7D: SparklineIn7D?,
    @SerialName("price_change_percentage_24h_in_currency")
    val priceChangePercentage24HInCurrency: Double?,
    @SerialName("current_holdings")
    val currentHoldings: Double? = null,
    val amount: Double? = null
) {
    val currentHoldingsValue: Double
        get() = (currentHoldings ?: 0.0) * (currentPrice ?: 0.0)

    val rank: Int
        get() = (marketCap ?: 0).toInt()
}

@Serializable
data class SparklineIn7D(
    val price: List<Double>?
)
