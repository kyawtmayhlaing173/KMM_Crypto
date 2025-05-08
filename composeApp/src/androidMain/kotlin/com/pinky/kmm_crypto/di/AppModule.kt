package com.pinky.kmm_crypto.di

import com.pinky.kmm_crypto.coin.CoinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CoinViewModel(get()) }
}