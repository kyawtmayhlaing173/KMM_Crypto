package com.pinky.kmm_crypto.presentation.coin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinky.kmm_crypto.data.remote.CoinModel
import com.pinky.kmm_crypto.database.CoinEntity
import com.pinky.kmm_crypto.domain.model.PortfolioCoin
import com.pinky.kmm_crypto.domain.usecase.GetCoinsUseCase
import com.pinky.kmm_crypto.domain.usecase.GetPortfolioCoinsUseCase
import com.pinky.kmm_crypto.domain.usecase.SavePortfolioCoinUseCase
import com.pinky.kmm_crypto.mapper.toCoin
import com.pinky.kmm_crypto.toCurrencyFormat
import kotlinx.coroutines.launch

enum class SortOptions {
    Rank,
    RankReversed,
    Holdings,
    HoldingsReversed,
    Price,
    PriceReversed
}

class CoinViewModel(
    val getCoinUseCase: GetCoinsUseCase,
    val getPortfolioCoinsUseCase: GetPortfolioCoinsUseCase,
    val savePortfolioCoin: SavePortfolioCoinUseCase,
): ViewModel() {
    var uiState by mutableStateOf(CoinScreenState())

    init {
        loadCoins()
    }

    private fun getPortfolioCoins() {
        viewModelScope.launch {
            getPortfolioCoinsUseCase.invoke()
                .collect { coinEntities -> // coinEntities is List<PortfolioCoinEntity>
                    val updatedCoinModels = coinEntities.mapNotNull { entity ->
                        uiState.coins.find { it.id == entity.coinId }?.let { foundCoinModel ->
                            foundCoinModel.copy(amount = entity.amount, currentHoldings = entity.amount)
                        }
                    }
                    uiState = uiState.copy(portfolioCoins = updatedCoinModels)
                }
        }
//        viewModelScope.launch {
//            getPortfolioCoinsUseCase.invoke()
//                .collect { coinEntities ->
//                    val coinModels = coinEntities.map { entity ->
//                        uiState.coins.find { it.id == entity.coinId }
//                    }
//                    uiState = uiState.copy(portfolioCoins = coinModels)
//                }
//        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            try {
                val resultCoins = getCoinUseCase()
                println("🔥 Result: $resultCoins")
                uiState = uiState.copy(
                    loading = false,
                    coins = resultCoins
                )
                getPortfolioCoins()
            } catch (e: Throwable) {
                println("🔥 Error: ${e.message}")
                uiState = uiState.copy(
                    loading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun onSearchTextChange(text: String) {
        uiState = uiState.copy(
            loading = true,
            searchText = text
        )
        val resultCoins = onSearchExecute(text)
        uiState = uiState.copy(
            loading = false,
            coins = resultCoins
        )
    }

    fun onCloseClick() {
        loadCoins()
    }

    fun onSortClick(sortOption: SortOptions) {
        uiState = uiState.copy(sortOption = sortOption)
        uiState = when (sortOption) {
            SortOptions.Rank, SortOptions.Holdings -> uiState.copy(coins = uiState.coins.sortedBy { it.marketCapRank })
            SortOptions.RankReversed, SortOptions.HoldingsReversed -> uiState.copy(coins = uiState.coins.sortedBy { it.marketCapRank }.reversed())
            SortOptions.Price -> uiState.copy(coins = uiState.coins.sortedBy { it.currentPrice })
            SortOptions.PriceReversed -> uiState.copy(coins = uiState.coins.sortedBy { it.currentPrice }.reversed())
        }
    }

    private fun onSearchExecute(query: String): List<CoinModel> {
        if (query.isEmpty()) {
            loadCoins()
        }
        return uiState.coins.filter {
            it.symbol.contains(query, ignoreCase = true)
        }.sortedBy {
            it.marketCapRank
        }
    }

    fun getCurrentValue(amount: String, currentPrice: Double?): String {
            val amountDouble = amount.toDoubleOrNull()
            if (amountDouble != null && currentPrice != null) {
                println("Amount is $amountDouble $currentPrice ${amountDouble * currentPrice}")
                return (amountDouble * currentPrice).toCurrencyFormat()
            }
            return "0.0"
    }

     fun onPortfolioSaved(coin: CoinModel, amount: String) {
        viewModelScope.launch {
            val coinEntity = CoinEntity(
                id = coin.id,
                coinId = coin.id,
                amount = amount.toDoubleOrNull() ?: 0.0,
            )
            savePortfolioCoin(coin = coinEntity)
        }
    }

    fun toCoinModel(portfolioCoin: PortfolioCoin): CoinModel? {
        val coin = uiState.coins.find { it.id == portfolioCoin.coinId }
        return coin
    }
}

data class CoinScreenState(
    var loading: Boolean = true,
    var coins: List<CoinModel> = listOf(),
    var errorMessage: String? = null,
    var searchText: String = "",
    var sortOption: SortOptions = SortOptions.Rank,
    var portfolioCoins: List<CoinModel?> = listOf()
)
