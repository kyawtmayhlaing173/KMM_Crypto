package com.pinky.kmm_crypto.data.remote

import io.ktor.client.call.body
import io.ktor.client.request.get

internal class CoinService: KtorApi() {
    suspend fun getCoins(): List<CoinModel> = client.get {
        pathURL(
            "coins/markets?vs_currency=usd&order=market_cap_desc&per_page=250&page=1&sparkline=true&price_change_percentage=24h",
        )
    }.body()

    suspend fun getMarketData(): GlobalData = client.get {
        pathURL("global")
    }.body()
}