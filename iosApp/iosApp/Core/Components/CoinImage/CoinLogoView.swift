//
//  CoinLogoView.swift
//  SwiftyCrypto
//
//  Created by Kyawt May Hlaing on 3/4/2566 BE.
//

import SwiftUI
import Shared

struct CoinLogoView: View {
    let coin: CoinModel
    
    var body: some View {
        VStack {
            CoinImageView(coin: coin)
                .frame(width: 50, height: 50)
            Text(coin.symbol.uppercased())
                .font(.headline)
                .foregroundColor(Color.theme.accent)
                .lineLimit(1)
                .minimumScaleFactor(0.5)
            Text(coin.name)
                .font(.caption)
                .foregroundColor(Color.theme.secondaryText)
                .lineLimit(2)
                .minimumScaleFactor(0.5)
                .multilineTextAlignment(.center)
        }
    }
}

//struct CoinLogoView_Previews: PreviewProvider {
//    static var previews: some View {
//        CoinLogoView(coin: dev.coin)
//            .previewLayout(.sizeThatFits)
//            .preferredColorScheme(.light)
//        CoinLogoView(coin: dev.coin)
//            .previewLayout(.sizeThatFits)
//            .preferredColorScheme(.dark)
//    }
//}
