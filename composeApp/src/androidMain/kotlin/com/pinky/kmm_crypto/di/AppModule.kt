package com.pinky.kmm_crypto.di

import com.pinky.kmm_crypto.presentation.coin.CoinViewModel
import com.pinky.kmm_crypto.presentation.market.MarketDataViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CoinViewModel(get(), get(), get()) }
    viewModel { MarketDataViewModel(get()) }
}