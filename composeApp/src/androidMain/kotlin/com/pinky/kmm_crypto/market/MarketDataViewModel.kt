package com.pinky.kmm_crypto.market

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinky.kmm_crypto.data.remote.CoinModel
import com.pinky.kmm_crypto.data.remote.MarketDataModel
import com.pinky.kmm_crypto.domain.usecase.GetMarketDataUseCase
import com.pinky.kmm_crypto.model.StatisticModel
import kotlinx.coroutines.launch

class MarketDataViewModel(
    val getMarketDataUseCase: GetMarketDataUseCase,
): ViewModel() {
    init {
        loadMarketData()
    }

    var statistics: List<StatisticModel> by mutableStateOf(emptyList())

    private fun loadMarketData() {
        println("Load Market Data")
        viewModelScope.launch {
            try {
                val result = getMarketDataUseCase()
                println("🔥🔥 Load Market Data: $result")
                statistics = mapGlobalMarketData(result.data, emptyList())
                println("🔥🔥🔥 Load Market Data: $statistics")
            } catch (e: Throwable) {
                print("Error: ${e.message}")
            }
        }
    }

    private fun mapGlobalMarketData(marketDataModel: MarketDataModel?, portfolioCoins: List<CoinModel>): List<StatisticModel> {
        var stats by mutableStateOf(emptyList<StatisticModel>())
        val data = marketDataModel ?: return stats

        val marketCap = StatisticModel(title = "Market Cap", value = data.marketCap, percentageChange = data.marketCapChangePercentage24hUsd)
        val volume = StatisticModel(title = "24h Volume", value = data.volume, percentageChange = null)
        val btcDominance = StatisticModel(title = "BTC Dominance", value = data.btcDominance, percentageChange = null)

        val portfolioValue = portfolioCoins.sumOf { it.currentHoldingsValue ?: 0.0 }
        val previousValue = portfolioCoins.sumOf {
            val currentValue = it.currentHoldingsValue
            val percentChange: Double = (it.priceChangePercentage24H ?: 0.0) / 100
            val previousValue = currentValue / (1.0 + percentChange)
            previousValue
        }

        val percentageChange = (portfolioValue - previousValue) / previousValue
        val portfolio = StatisticModel(title = "Portfolio Value", value = portfolioValue, percentageChange)

        stats = listOf(
            marketCap,
            volume,
            btcDominance,
            portfolio
        )
        return stats
    }
}