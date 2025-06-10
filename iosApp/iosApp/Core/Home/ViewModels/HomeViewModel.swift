//
//  HomeViewModel.swift
//  SwiftyCrypto
//
//  Created by Kyawt May Hlaing on 6/3/2566 BE.
//

import Foundation
import Combine
import Shared

@MainActor class HomeViewModel: ObservableObject {
    private let getCoinUseCase = GetCoinsUseCase.init()
    
    @Published var allCoins: [CoinModel] = []
    @Published var displayedCoins: [CoinModel] = []

    @Published var portfolioCoins: [CoinModel] = []
    @Published var searchText: String = ""
    @Published var statistics: [StatisticModel] = []
    @Published var isLoading: Bool = false
    @Published var sortOption: SortOption = .holdings
    
    private var cancellables = Set<AnyCancellable>()
    
    enum SortOption {
        case rank, rankReversed, holdings, holdingsReversed, price, priceReversed
    }
    
    init() {
        $searchText
            .debounce(for: .milliseconds(300), scheduler: RunLoop.main)
            .removeDuplicates()
            .map{ $0.lowercased() }
            .sink { [weak self] mappedText in
                self?.searchCoins(searchText: mappedText)
            }
            .store(in: &cancellables)
    }
    
    func loadSubscribers() async {
        await loadCoins()
        await loadStatistics()
    }
    
    func loadStatistics() async {
        do {
            let marketData = try await GetMarketDataUseCase().invoke()
            print("Market Data: \(marketData)")
            statistics = mapGlobalMarketData(marketDataModel: marketData.data, portfolioCoins: allCoins)
            print("Statistics: \(statistics.count)")
        } catch {
            print("Error is \(error)")
        }
    }
    
    func loadCoins() async {
        do {
            let coins = try await GetCoinsUseCase().invoke()
            allCoins = coins
            displayedCoins = coins
        } catch {
            print("⛔️ Error is \(error)")
        }
    }
        
//    func mapAllCoinsToPortfolioCoins(allCoins: [CoinModel], portfolioEntities: [PortfolioEntity]) -> [CoinModel] {
//        allCoins.compactMap { coin -> CoinModel? in
//            guard let entity = portfolioEntities.first(where: { $0.coinID == coin.id }) else {
//                return nil
//            }
//            return coin.updateHoldings(amount: entity.amount)
//        }
//
//    }
//    
//    func updatePortfolio(coin: CoinModel, amount: Double) {
//        portfolioDataService.updatePortfolio(coin: coin, amount: amount)
//    }

    func searchCoins(searchText: String) {
        displayedCoins = filterAndSortCoins(text: searchText, coins: allCoins, sortOption: SortOption.rank)
    }
    
    private func filterAndSortCoins(text: String, coins: [CoinModel], sortOption: SortOption) -> [CoinModel] {
        var updatedCoins = filterCoins(text: text, coins: coins)
        sortCoins(sort: sortOption, coins: &updatedCoins)
        return updatedCoins
    }
    
    private func filterCoins(text: String, coins: [CoinModel]) -> [CoinModel] {
        guard !text.isEmpty else {
            return coins
        }
        let lowercasedText = text.lowercased()
        return coins.filter { coin -> Bool in
            return coin.name.lowercased().contains(lowercasedText) || coin.symbol.lowercased().contains(lowercasedText) || coin.id.lowercased().contains(lowercasedText)
        }
    }
    
    private func sortCoins(sort: SortOption, coins: inout [CoinModel]) {
        switch sort {
        case .rank, .holdings:
            coins.sort(by: { $0.rank < $1.rank })
        case .rankReversed, .holdingsReversed:
            coins.sort(by: { $0.rank > $1.rank })
        case .price:
            coins.sort(by: { ($0.currentPrice?.doubleValue ?? 0.00) > ($1.currentPrice?.doubleValue ?? 0.00) })
        case .priceReversed:
            coins.sort(by: { ($0.currentPrice?.doubleValue ?? 0.00) < ($1.currentPrice?.doubleValue ?? 0.00) })
        }
    }
    
    private func sortPortfolioCoinsIfNeeded(coins: [CoinModel]) -> [CoinModel] {
        // will only sort by holdings or reverseholdings if needed
        switch sortOption {
        case .holdings:
            return coins.sorted(by: { $0.currentHoldingsValue > $1.currentHoldingsValue })
        case .holdingsReversed:
            return coins.sorted(by: { $0.currentHoldingsValue < $1.currentHoldingsValue })
        default:
            return coins
        }
    }
    
    private func mapGlobalMarketData(marketDataModel: MarketDataModel?, portfolioCoins: [CoinModel]) -> [StatisticModel] {
        var stats: [StatisticModel] = []
        guard let data = marketDataModel else {
            return stats
        }
        let marketCap = StatisticModel(title: "Market Cap", value: "\(data.marketCap.formattedWithAbbreviations())", percentageChange: data.marketCapChangePercentage24hUsd)
        let volume = StatisticModel(title: "24h Volume", value: String(data.volume.formattedWithAbbreviations()))
        let btcDominance = StatisticModel(title: "BTC Dominance", value: data.btcDominance.asPercentString())
        
        let portfolioValue = portfolioCoins
            .map({ $0.currentHoldingsValue })
            .reduce(0, +)
        
        let previousValue = portfolioCoins.map { coin -> Double in
            let currentValue = coin.currentHoldingsValue
            let percentChange = coin.priceChangePercentage24H ?? KotlinDouble(integerLiteral: 0 / 100)
            let previousValue = currentValue / (1 + Double(truncating: percentChange))
            return previousValue
        }.reduce(0, +)
        let percentageChange = ((portfolioValue - previousValue) / previousValue)
        
        let portfolio = StatisticModel(title: "Portfolio Value", value: portfolioValue.asCurrencyWith2Decimals(), percentageChange: percentageChange)

        stats.append(contentsOf: [
            marketCap,
            volume,
            btcDominance,
            portfolio
        ])
        return stats
    }
    
    func reloadData() {
        isLoading = true
//        coinDataService.getCoins()
//        marketDataService.getData()
//        HapticManager.notification(type: .success)
    }
}
