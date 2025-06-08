package com.pinky.kmm_crypto.data.remote

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class CoinService: KtorApi() {
    suspend fun getCoins(): List<CoinModel> = client.get(urlString = "$BASE_URL/coins/markets") {
        parameter("vs_currency", "usd")
        parameter("order", "market_cap_desc")
        parameter("per_page", 250)
        parameter("page", 1)
        parameter("sparkline", true)
        parameter("price_change_percentage", "24h")
    }.body()

    suspend fun getMarketData(): GlobalData = client.get(urlString = "$BASE_URL/global").body()
}