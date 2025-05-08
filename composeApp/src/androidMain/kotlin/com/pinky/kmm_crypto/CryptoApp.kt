package com.pinky.kmm_crypto

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pinky.kmm_crypto.coin.CoinRow
import com.pinky.kmm_crypto.coin.CoinViewModel
import com.pinky.kmm_crypto.coin.SearchTextField
import com.pinky.kmm_crypto.coin.SortOptions
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CryptoApp() {
    val viewModel = koinViewModel<CoinViewModel>()
    val uiState = viewModel.uiState

    Scaffold(
        contentWindowInsets = WindowInsets.systemBars
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = padding)
        ) {
            if (uiState.loading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                SearchTextField(
                    text = uiState.searchText,
                    onTextChange = viewModel::onSearchTextChange,
                    onCloseClick = viewModel::onCloseClick
                )
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Coin", style = TextStyle(color = Color.Gray))
                        Spacer(modifier = Modifier.width(8.dp))

                        Crossfade(
                            targetState = uiState.sortOption,
                            animationSpec = tween(durationMillis = 300),
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    viewModel.onSortClick(
                                        when (uiState.sortOption) {
                                            SortOptions.Rank -> SortOptions.RankReversed
                                            SortOptions.RankReversed -> SortOptions.Rank
                                            else -> SortOptions.Rank
                                        }
                                    )
                                },
                            label = "Coin"
                        ) { currentSortOption ->
                            Image(
                                imageVector =
                                if (currentSortOption == SortOptions.Rank)
                                    Icons.Default.KeyboardArrowDown
                                else
                                    Icons.Default.KeyboardArrowUp,
                                contentDescription = "Sort Arrow"
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Text(text = "Price", style = TextStyle(color = Color.Gray))
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Icon",
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    viewModel.onSortClick(
                                        when (uiState.sortOption) {
                                            SortOptions.Price -> SortOptions.PriceReversed
                                            SortOptions.PriceReversed -> SortOptions.Price
                                            else -> SortOptions.Price
                                        }
                                    )
                                }
                        )
                    }
                    LazyColumn(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        items(uiState.coins) {
                            CoinRow(coin = it)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CryptoAppPreview() {
    CryptoApp()
}

