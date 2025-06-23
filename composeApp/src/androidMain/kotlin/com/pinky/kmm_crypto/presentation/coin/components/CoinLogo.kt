package com.pinky.kmm_crypto.presentation.coin.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.pinky.kmm_crypto.data.remote.CoinModel
import com.pinky.kmm_crypto.ui.theme.sectionSubtitle
import com.pinky.kmm_crypto.ui.theme.small

@Composable
fun CoinLogo(
    symbol: String,
    name: String,
    image: String,
    isCoinSelected: Boolean,
    onCoinSelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .border(
                width = 1.dp,
                color = if (isCoinSelected) Color.Blue else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                onCoinSelected()

            }
    ) {
        Column(
            modifier = Modifier
                .width(100.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            image.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Coin Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = symbol.toUpperCase(Locale.current),
                style = MaterialTheme.typography.sectionSubtitle,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.small,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }

}

@Preview
@Composable
fun CoinLogoPreview() {

}