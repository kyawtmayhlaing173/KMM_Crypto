package com.pinky.kmm_crypto

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pinky.kmm_crypto.coin.CoinRow
import com.pinky.kmm_crypto.coin.CoinViewModel
import com.pinky.kmm_crypto.market.MarketDataViewModel
import com.pinky.kmm_crypto.coin.SearchTextField
import com.pinky.kmm_crypto.coin.SortOptions
import com.pinky.kmm_crypto.market.HomeStatView
import com.pinky.kmm_crypto.market.PortfolioEmptyText
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CryptoApp() {
    val coinViewModel = koinViewModel<CoinViewModel>()
    val marketViewModel = koinViewModel<MarketDataViewModel>()
    val uiState = coinViewModel.uiState

    var showPortfolio by remember { mutableStateOf(false) }

    Scaffold(
        contentWindowInsets = WindowInsets.systemBars
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = padding)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Surface(
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    shadowElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        // AnimatedContent for smooth transition between icons
                        AnimatedContent(
                            targetState = showPortfolio,
                            transitionSpec = {
                                (fadeIn(animationSpec = tween(300)) +
                                        scaleIn(initialScale = 0.8f, animationSpec = tween(300))) togetherWith
                                        (fadeOut(animationSpec = tween(300)) +
                                                scaleOut(targetScale = 0.8f, animationSpec = tween(300)))
                            },
                            label = "IconTransition"
                        ) { targetState ->
                            Icon(
                                imageVector = if (targetState) Icons.Outlined.Add else Icons.Outlined.Info,
                                contentDescription = if (targetState) "Add Icon" else "Info Icon",
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable { showPortfolio = !showPortfolio }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = if (showPortfolio) "Portfolio" else "Live Prices",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,

                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Surface(
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            showPortfolio = !showPortfolio
                        },
                    shape = CircleShape,
                    shadowElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        AnimatedContent(
                            targetState = showPortfolio,
                            transitionSpec = {
                                fadeIn(animationSpec = tween(300)) +
                                        scaleIn(
                                            initialScale = 0.8f,
                                            animationSpec = tween(300)
                                        ) togetherWith fadeOut(animationSpec = tween(300)) +
                                                scaleOut(
                                                    targetScale = 0.8f,
                                                    animationSpec = tween(300)
                                                )
                            },
                            label = "IconTransition"
                        ) { _ ->
                            Icon(
                                imageVector =
                                if (showPortfolio) {
                                    Icons.AutoMirrored.Outlined.KeyboardArrowLeft
                                } else {
                                    Icons.AutoMirrored.Outlined.KeyboardArrowRight
                                },
                                contentDescription = "Chevron Right",
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                    }
                }
            }

            HomeStatView(viewModel = marketViewModel, showPortfolio = showPortfolio)

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
                    onTextChange = coinViewModel::onSearchTextChange,
                    onCloseClick = coinViewModel::onCloseClick
                )

                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Coin
                        Text(text = "Coin", style = TextStyle(color = Color.Gray))
                        Spacer(modifier = Modifier.width(8.dp))
                        Crossfade(
                            targetState = uiState.sortOption,
                            animationSpec = tween(durationMillis = 300),
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    coinViewModel.onSortClick(
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

                        if (showPortfolio) {
                            // Holdings
                            Text(text = "Holdings", style = TextStyle(color = Color.Gray))
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Holdings Icon",
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable {  }
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        // Price
                        Text(text = "Price", style = TextStyle(color = Color.Gray))
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Icon",
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    coinViewModel.onSortClick(
                                        when (uiState.sortOption) {
                                            SortOptions.Price -> SortOptions.PriceReversed
                                            SortOptions.PriceReversed -> SortOptions.Price
                                            else -> SortOptions.Price
                                        }
                                    )
                                }
                        )
                    }

                    if (!showPortfolio) {
                        LazyColumn(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            items(uiState.coins) {
                                CoinRow(coin = it)
                            }
                        }
                    } else {
                        PortfolioEmptyText()
                    }
                }
            }
        }
    }
}

@Composable
fun CoinHeader() {

}

@Preview
@Composable
fun CryptoAppPreview() {
    CryptoApp()
}

