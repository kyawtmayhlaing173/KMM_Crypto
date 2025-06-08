package com.pinky.kmm_crypto.settings

import Gray20
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pinky.kmm_crypto.ui.theme.mainTitle
import com.pinky.kmm_crypto.ui.theme.sectionSubtitle
import com.pinky.kmm_crypto.ui.theme.sectionTitle
import kmm_crypto.composeapp.generated.resources.Res
import kmm_crypto.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import java.util.Locale
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun SettingScreen(padding: PaddingValues, onBackClick: () -> Unit) {
    Box(modifier = Modifier
        .background(Gray20)
        .padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Back",
                    tint = Color.Black,
                )
            }
            // Main Title
            Text(
                "Settings",
                style = MaterialTheme.typography.mainTitle,
                modifier = Modifier.padding(16.dp)
            )

            // Author Section
            SectionTitle("Swiftful Thinking")
            SectionContent {
                Icon(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "Coin Image",
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "This app was made by following a @SwiftfulThinking course...",
                    style = MaterialTheme.typography.sectionSubtitle
                )
                DividerWithSpacing()
                ClickableText("Subscribe on Youtube 🎥", url = "https://www.youtube.com/watch?v=F469PgQ4hvI")
                DividerWithSpacing()
                ClickableText("Support his coffee addition ☕️", url = "https://www.youtube.com/watch?v=F469PgQ4hvI")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // CoinGecko Section
            SectionTitle("CoinGecko")
            SectionContent {
                Icon(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "Coin Gecko",
                    modifier = Modifier.width(200.dp)
                )
                Text(
                    "The cryptocurrency data that is used in this app...",
                    style = MaterialTheme.typography.sectionSubtitle
                )
                DividerWithSpacing()
                ClickableText("Visit CoinGecko 🦎", url = "https://www.coingecko.com")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Application Section
            SectionTitle("Application")
            SectionContent {
                ClickableText("Terms of Service", url = "https://www.youtube.com/watch?v=F469PgQ4hvI")
                DividerWithSpacing()
                ClickableText("Privacy Policy", url = "https://www.youtube.com/watch?v=F469PgQ4hvI")
                DividerWithSpacing()
                ClickableText("Company Website", url = "https://www.youtube.com/watch?v=F469PgQ4hvI")
                DividerWithSpacing()
                ClickableText("Learn More", url = "https://www.youtube.com/watch?v=F469PgQ4hvI")
            }
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text.uppercase(Locale.getDefault()),
        style = MaterialTheme.typography.sectionTitle,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun ClickableText(text: String, url: String) {
    val uriHandler = LocalUriHandler.current

    println("URL: $url")
    // Validate URL before attempting to open
    if (url.isBlank() || !url.startsWith("https")) {
        println("Invalid URL: $url")
        return
    }

    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline
        ),
        modifier = Modifier
            .clickable {
                println("Opening URL: $url")
                uriHandler.openUri(url)
            }
            .padding(vertical = 8.dp)
    )
}

@Composable
fun SectionContent(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        content()
    }
}

@Composable
fun DividerWithSpacing() {
    Spacer(modifier = Modifier.height(8.dp))
    Divider()
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
fun SettingScreenPreview() {
    SettingScreen(padding = PaddingValues(8.dp), onBackClick = {})
}