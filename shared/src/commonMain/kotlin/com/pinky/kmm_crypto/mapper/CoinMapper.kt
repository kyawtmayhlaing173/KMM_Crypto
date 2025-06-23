package com.pinky.kmm_crypto.mapper

import com.pinky.kmm_crypto.data.remote.CoinModel
import com.pinky.kmm_crypto.database.CoinEntity
import com.pinky.kmm_crypto.domain.model.PortfolioCoin

fun CoinEntity.toCoin(): PortfolioCoin {
    return PortfolioCoin(
        coinId = coinId,
        amount = amount,
    )
}
