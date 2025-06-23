package com.pinky.kmm_crypto.presentation.market

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeStatView(
    modifier: Modifier = Modifier,
    viewModel: MarketDataViewModel,
    showPortfolio: Boolean = false
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = showPortfolio) {
        scrollState.animateScrollTo(if (scrollState.value == 0) (scrollState.maxValue - 50) else 0)
    }

    Column(
        modifier = Modifier.padding(0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
                .horizontalScroll(scrollState)
        ) {
            viewModel.statistics.forEach { stat ->
                Box(
                    modifier = Modifier
                        .width(LocalConfiguration.current.screenWidthDp.dp / 3f)
                        .padding(8.dp)
                ) {
                    StatisticView(stat = stat)
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeStatViewPreview() {
}