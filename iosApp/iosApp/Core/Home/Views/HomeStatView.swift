//
//  HomeStatView.swift
//  SwiftyCrypto
//
//  Created by Kyawt May Hlaing on 30/3/2566 BE.
//

import SwiftUI

struct HomeStatView: View {
    @EnvironmentObject private var homeVM: HomeViewModel
    @Binding var showPortfolio: Bool
    
    var body: some View {
        HStack {
            ForEach(homeVM.statistics) { stat in
                StatisticView(stat: stat)
                    .frame(width: UIScreen.main.bounds.width / 3)
            }
        }
        .frame(width: UIScreen.main.bounds.width, alignment: showPortfolio ? .trailing : .leading)
    }
}

struct HomeStatView_Previews: PreviewProvider {
    static var previews: some View {
        HomeStatView(showPortfolio: .constant(false))
            .environmentObject(HomeViewModel())
    }
}
