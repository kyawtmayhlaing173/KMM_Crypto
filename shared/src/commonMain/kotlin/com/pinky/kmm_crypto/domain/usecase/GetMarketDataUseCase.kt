package com.pinky.kmm_crypto.domain.usecase

import com.pinky.kmm_crypto.data.remote.GlobalData
import com.pinky.kmm_crypto.domain.repository.MarketDataRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetMarketDataUseCase: KoinComponent {
    private val repository: MarketDataRepository by inject()

    suspend operator fun invoke(): GlobalData {
        return repository.getMarketData()
    }
}