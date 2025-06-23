package com.pinky.kmm_crypto.domain.usecase

import com.pinky.kmm_crypto.data.remote.CoinModel
import com.pinky.kmm_crypto.database.CoinEntity
import com.pinky.kmm_crypto.domain.repository.CoinRepository
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SavePortfolioCoinUseCase: KoinComponent {
    private val repository: CoinRepository by inject()

    suspend operator fun invoke(coin: CoinEntity): EmptyResult<DataError.Local> {
        return repository.savePortfolioCoin(coin)
    }
}