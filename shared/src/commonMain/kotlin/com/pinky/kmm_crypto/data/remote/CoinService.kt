package com.pinky.kmm_crypto.data.remote

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.parameters

internal class CoinService: KtorApi() {
    suspend fun getCoins(): List<CoinModel> = client.get {
        pathURL("coins/markets")
        parameters {
            append("vs_currency", "usd")
            append("order", "market_cap_desc")
            append("per_page", "250")
            append("page", "1")
            append("sparkline", "true")
            append("price_change_percentage", "24h")
        }
    }.body()
}