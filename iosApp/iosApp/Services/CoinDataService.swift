//
//  CoinDataService.swift
//  SwiftyCrypto
//
//  Created by Kyawt May Hlaing on 12/3/2566 BE.
//

import SwiftUI
import Combine
import Shared

@MainActor class CoinDataService {
    @Published var allCoins: [CoinModel] = []
    var coinSubscription: AnyCancellable?
    private let getCoinUseCase = GetCoinsUseCase.init()

    init() async {
        await getCoins()
    }
    
    func getCoins() async {
//        self.allCoins = try await getCoinUseCase.invoke()
    }
}
