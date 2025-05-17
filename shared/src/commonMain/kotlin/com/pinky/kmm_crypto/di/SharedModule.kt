package com.pinky.kmm_crypto.di

import com.pinky.kmm_crypto.data.remote.CoinService
import com.pinky.kmm_crypto.data.remote.RemoteDataSource
import com.pinky.kmm_crypto.data.repository.CoinRepositoryImpl
import com.pinky.kmm_crypto.data.repository.MarketDataRepositoryImpl
import com.pinky.kmm_crypto.domain.repository.CoinRepository
import com.pinky.kmm_crypto.domain.repository.MarketDataRepository
import com.pinky.kmm_crypto.domain.usecase.GetCoinsUseCase
import com.pinky.kmm_crypto.domain.usecase.GetMarketDataUseCase
import com.pinky.kmm_crypto.utils.provideDispatcher
import org.koin.dsl.module

private val dataModule = module {
    factory { RemoteDataSource(get(), get()) }
    factory { CoinService() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

private val domainModule = module {
    single<CoinRepository> { CoinRepositoryImpl(get()) }
    single<MarketDataRepository> { MarketDataRepositoryImpl(get()) }
    factory { GetCoinsUseCase() }
    factory { GetMarketDataUseCase() }
}

private val sharedModules = listOf(dataModule, utilityModule, domainModule)

fun getSharedModules() = sharedModules