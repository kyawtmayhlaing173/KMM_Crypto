package com.pinky.kmm_crypto.market

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeStatView(
    modifier: Modifier = Modifier,
    viewModel: MarketDataViewModel
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
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

@Preview
@Composable
fun HomeStatViewPreview() {
//    HomeStatView(modifier = Modifier)
}