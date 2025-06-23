package com.pinky.kmm_crypto

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pinky.kmm_crypto.presentation.coin.CoinViewModel
import com.pinky.kmm_crypto.presentation.coin.HomeScreen
import com.pinky.kmm_crypto.presentation.market.MarketDataViewModel
import com.pinky.kmm_crypto.common.Home
import com.pinky.kmm_crypto.common.Portfolio
import com.pinky.kmm_crypto.common.Setting
import com.pinky.kmm_crypto.presentation.portfolioCoin.PortfolioView
import com.pinky.kmm_crypto.presentation.settings.SettingScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CryptoApp() {
    val navController = rememberNavController()

    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Home.routeWithArgs
        ) {
            composable(Home.routeWithArgs) {
                val coinViewModel = koinViewModel<CoinViewModel>()
                val marketViewModel = koinViewModel<MarketDataViewModel>()

                HomeScreen(
                    coinViewModel = coinViewModel,
                    marketViewModel = marketViewModel,
                    padding = padding,
                    navigateToSetting = {
                        navController.navigate(Setting.route)
                    },
                    navigateToPortfolio = {
                        navController.navigate(Portfolio.route)
                    }
                )
            }

            composable(Setting.routeWithArgs) {
                BackHandler {
                    navController.popBackStack()
                }
                SettingScreen(
                    padding = padding,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Portfolio.routeWithArgs) {
                val coinViewModel = koinViewModel<CoinViewModel>()

                BackHandler {
                    navController.popBackStack()
                }
                PortfolioView(
                    coinViewModel = coinViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun CryptoAppPreview() {
    CryptoApp()
}

