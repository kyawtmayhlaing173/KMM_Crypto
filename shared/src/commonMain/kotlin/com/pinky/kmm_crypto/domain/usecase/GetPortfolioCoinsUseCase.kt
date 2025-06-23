package com.pinky.kmm_crypto.domain.usecase

import com.pinky.kmm_crypto.data.remote.CoinModel
import com.pinky.kmm_crypto.database.CoinEntity
import com.pinky.kmm_crypto.domain.model.PortfolioCoin
import com.pinky.kmm_crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetPortfolioCoinsUseCase: KoinComponent {
    private val repository: CoinRepository by inject()

    suspend operator fun invoke(): Flow<List<CoinEntity>> {
        return repository.getPortfolioCoins()
    }
}