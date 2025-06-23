package com.pinky.kmm_crypto.presentation.portfolioCoin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pinky.kmm_crypto.data.remote.CoinModel
import com.pinky.kmm_crypto.database.CoinEntity
import com.pinky.kmm_crypto.presentation.coin.CoinViewModel
import com.pinky.kmm_crypto.presentation.coin.SearchTextField
import com.pinky.kmm_crypto.presentation.coin.components.CoinLogo
import com.pinky.kmm_crypto.toCurrencyFormat
import com.pinky.kmm_crypto.ui.theme.mainTitle
import com.pinky.kmm_crypto.ui.theme.sectionSubtitle
import androidx.lifecycle.viewModelScope

@Composable
fun PortfolioView (
    coinViewModel: CoinViewModel,
    onBackClick: () -> Unit,
) {
    val uiState = coinViewModel.uiState
    var selectedCoin by remember { mutableStateOf<CoinModel?>(null) }
    var amount by remember { mutableStateOf<String>("") }

    Box {
        Column(modifier = Modifier
            .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close Icon")
            }

            Text(
                "Edit Portfolio",
                style = MaterialTheme.typography.mainTitle,
                modifier = Modifier.padding(16.dp)
            )

            SearchTextField(
                text = uiState.searchText,
                onTextChange = coinViewModel::onSearchTextChange,
                onCloseClick = coinViewModel::onCloseClick
            )

            if (uiState.searchText.isNotEmpty()) {
                LazyRow(modifier = Modifier.padding(16.dp)) {
                    items(uiState.coins) { coin ->
                        CoinLogo(
                            symbol = coin.symbol,
                            name = coin.name,
                            image = coin.image,
                            isCoinSelected = selectedCoin?.name == coin.name,
                            onCoinSelected = { selectedCoin = coin }
                        )
                    }
                }
            } else {
                LazyRow(modifier = Modifier.padding(16.dp)) {
                    items(uiState.portfolioCoins) { coin ->
                        CoinLogo(
                            symbol = coin?.symbol ?: "",
                            name = coin?.name ?: "",
                            image = coin?.image ?: "",
                            isCoinSelected = selectedCoin?.name == (coin?.name ?: ""),
                        ) {
                            amount = coin?.amount.toString()
                            selectedCoin = coin
                        }
                    }
                }
            }

            if (selectedCoin != null) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Text(
                            "Current price of ${selectedCoin?.name}:",
                            style = MaterialTheme.typography.sectionSubtitle
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            "${selectedCoin?.currentPrice?.toCurrencyFormat()}",
                            style = MaterialTheme.typography.sectionSubtitle
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text("Amount in the portfolio:", style = MaterialTheme.typography.sectionSubtitle)
                        Spacer(modifier = Modifier.weight(1f))
                        BasicTextField(
                            value = amount,
                            onValueChange = {
                                println("On Value Change $amount")
                                amount = it
                            },
                            textStyle = MaterialTheme.typography.titleSmall.copy(textAlign = TextAlign.End)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(
                            "Current value:",
                            style = MaterialTheme.typography.sectionSubtitle
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            coinViewModel.getCurrentValue(amount, selectedCoin?.currentPrice),
                            style = MaterialTheme.typography.sectionSubtitle
                        )
                    }
                    Spacer(modifier = Modifier.height(32.dp))

                    if (amount.isNotEmpty()) {
                        Button(onClick = {
                            if (selectedCoin != null) {
                                coinViewModel.onPortfolioSaved(selectedCoin!!, amount)
                            }
                        }) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}