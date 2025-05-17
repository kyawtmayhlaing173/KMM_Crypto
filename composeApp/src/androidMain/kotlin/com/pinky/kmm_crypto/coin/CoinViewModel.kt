package com.pinky.kmm_crypto.coin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinky.kmm_crypto.data.remote.CoinModel
import com.pinky.kmm_crypto.domain.usecase.GetCoinsUseCase
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
): ViewModel() {
    var uiState by mutableStateOf(CoinScreenState())

    init {
        loadCoins()
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
}

data class CoinScreenState(
    var loading: Boolean = true,
    var coins: List<CoinModel> = listOf(),
    var errorMessage: String? = null,
    var searchText: String = "",
    var sortOption: SortOptions = SortOptions.Rank
)
