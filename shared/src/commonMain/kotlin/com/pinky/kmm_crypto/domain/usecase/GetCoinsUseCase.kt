package com.pinky.kmm_crypto.domain.usecase

import com.pinky.kmm_crypto.data.remote.CoinModel
import com.pinky.kmm_crypto.domain.repository.CoinRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetCoinsUseCase: KoinComponent {
    private val repository: CoinRepository by inject()

    suspend operator fun invoke(): List<CoinModel> {
        return repository.getCoins()
    }
}