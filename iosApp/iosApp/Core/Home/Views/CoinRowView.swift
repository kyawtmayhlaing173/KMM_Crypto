//
//  CoinRowView.swift
//  SwiftyCrypto
//
//  Created by Kyawt May Hlaing on 5/3/2566 BE.
//

import SwiftUI
import Shared

struct CoinRowView: View {
    let coin: CoinModel
    let showHoldingsColumn: Bool

    var body: some View {
        HStack(spacing: 0) {
            leftColumn
            Spacer()
            if showHoldingsColumn {
                centerColumn
            }
            rightColumn
        }
        .font(.subheadline)
        .background(
            Color.theme.background.opacity(0.001)
        )
    }
}

extension CoinRowView {
    private var leftColumn: some View {
        HStack {
            if let rank = coin.marketCapRank {
                Text("\(rank)")
                    .font(.caption)
                    .foregroundColor(Color.theme.secondaryText)
                    .frame(minWidth: 30)
            }
            CoinImageView(coin: coin)
                .frame(width: 30, height: 30)
            Text(coin.symbol.uppercased())
                .font(.headline)
                .padding(.leading, 6)
                .foregroundColor(Color.theme.accent)
        }
    }
    
    private var centerColumn: some View {
        VStack(alignment: .trailing) {
            Text(coin.currentHoldingsValue.asCurrencyWith2Decimals())
                .bold()
            Text("\(coin.currentHoldings ?? 0)")
        }
        .foregroundColor(Color.theme.accent)
    }
    
    private var rightColumn: some View {
        VStack(alignment: .trailing) {
            if let currentPrice = coin.currentPrice {
                Text("\(currentPrice.doubleValue.asCurrencyWith6Decimals())")
                    .bold()
                    .foregroundColor(Color.theme.accent)
            }
            
            let priceChangePercentage = (coin.priceChangePercentage24H?.doubleValue) ?? 0.00
            Text(priceChangePercentage.asPercentString())
                .foregroundColor(priceChangePercentage >= 0 ? Color.theme.green : Color.theme.red)
                
        }
        .frame(width: UIScreen.main.bounds.width / 3.5, alignment: .trailing)
    }
}

struct CoinRowView_Previews: PreviewProvider {
    static var previews: some View {
        CoinRowView(coin: DeveloperPreview.instance.coin, showHoldingsColumn: true)
            .preferredColorScheme(.light)
        CoinRowView(coin: DeveloperPreview.instance.coin, showHoldingsColumn: false)
            .preferredColorScheme(.dark)
    }
}
