package com.pinky.kmm_crypto.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.pinky.kmm_crypto.data.remote.CoinService
import com.pinky.kmm_crypto.data.remote.RemoteDataSource
import com.pinky.kmm_crypto.data.repository.CoinRepositoryImpl
import com.pinky.kmm_crypto.data.repository.MarketDataRepositoryImpl
import com.pinky.kmm_crypto.database.DatabaseFactory
import com.pinky.kmm_crypto.database.PortfolioCoinDatabase
import com.pinky.kmm_crypto.domain.repository.CoinRepository
import com.pinky.kmm_crypto.domain.repository.MarketDataRepository
import com.pinky.kmm_crypto.domain.usecase.GetCoinsUseCase
import com.pinky.kmm_crypto.domain.usecase.GetMarketDataUseCase
import com.pinky.kmm_crypto.domain.usecase.GetPortfolioCoinsUseCase
import com.pinky.kmm_crypto.domain.usecase.SavePortfolioCoinUseCase
import com.pinky.kmm_crypto.utils.provideDispatcher
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

private val dataModule = module {
    factory { RemoteDataSource(get(), get()) }
    factory { CoinService() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

private val domainModule = module {
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<PortfolioCoinDatabase>().portfolioCoinDao }
//    single<CoinRepository> { CoinRepositoryImpl(get(), get()) }

    singleOf(::CoinRepositoryImpl).bind<CoinRepository>()
    single<MarketDataRepository> { MarketDataRepositoryImpl(get()) }

    factory { GetCoinsUseCase() }
    factory { GetMarketDataUseCase() }
    factory { GetPortfolioCoinsUseCase() }
    factory { SavePortfolioCoinUseCase() }
}

private val sharedModules = listOf(dataModule, utilityModule, domainModule)

fun getSharedModules() = sharedModules