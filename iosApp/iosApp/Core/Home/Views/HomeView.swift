//
//  HomeView.swift
//  iosApp
//
//  Created by Kyawt May Hlaing on 08/06/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct HomeView: View {
    
    @State private var showPortfolio: Bool = false
    @StateObject var homeVM = HomeViewModel()
    @State private var showPortfolioView: Bool = false
    
    @State private var selectedCoin: CoinModel?
    @State private var showDetailView: Bool = false
    @State private var showSettingsView: Bool = false
    
    @State private var showContent = false
    var body: some View {
        VStack {
            homeHeader
            HomeStatView(showPortfolio: $showPortfolio)
                .environmentObject(homeVM)
            SearchBarView(searchText: $homeVM.searchText)
            columnTitles
            if !showPortfolio {
                allCoinsList.transition(.move(edge: .leading))
                    .transition(.move(edge: .leading))
            }
            if showPortfolio {
                ZStack(alignment: .top) {
                    if homeVM.portfolioCoins.isEmpty && homeVM.searchText.isEmpty {
                        portfolioEmptyText
                    } else {
                        portfolioCoinsList
                    }
                }
                .transition(.move(edge: .trailing))
            }
            
            Spacer(minLength: 0)
        }
        .sheet(isPresented: $showSettingsView) {
            SettingsView()
        }
        .task {
            await homeVM.loadSubscribers()
        }
        .background(
            NavigationLink(isActive: $showDetailView, destination: {
//                DetailLoadingView(coin: selectedCoin)
            }, label: {
                EmptyView()
            })
        )
    }
}

extension HomeView {
    private var homeHeader: some View {
        HStack {
            CircleButtonView(iconImage: showPortfolio ? "plus" : "info")
                .background(
                    CircleButtonAnimationView(animate: $showPortfolio)
                )
                .onTapGesture {
                    if showPortfolio {
                        showPortfolioView.toggle()
                    } else {
                        showSettingsView.toggle()
                    }
                }
            Spacer()
            Text(showPortfolio ? "Portfolio" : "Live Prices")
                .font(.headline)
                .fontWeight(.heavy)
            Spacer()
            CircleButtonView(iconImage: showPortfolio ? "chevron.left" : "chevron.right")
                .rotationEffect(Angle(degrees: showPortfolio ? 360 : 0))
                .onTapGesture {
                    withAnimation {
                        showPortfolio.toggle()
                        print("Show Portfolio is \(showPortfolio)")
                    }
                }
        }
        .padding(.horizontal)
        .sheet(isPresented: $showPortfolioView) {
            PortfolioView()
                .environmentObject(homeVM)
        }
    }
    
    private var portfolioCoinsList: some View {
        List {
            ForEach(homeVM.portfolioCoins, id: \.id) { coin in
                CoinRowView(coin: coin, showHoldingsColumn: true)
                    .listRowInsets(.init(top: 10, leading: 0, bottom: 10, trailing: 10))
                    .onTapGesture {
                        segue(coin: coin)
                    }
                    .listRowBackground(Color.theme.background)
            }
        }
        .listStyle(PlainListStyle())
    }
    
    
    private var allCoinsList: some View {
        List {
            ForEach(homeVM.displayedCoins, id: \.id) { coin in
                CoinRowView(coin: coin, showHoldingsColumn: false)
                    .onTapGesture {
                        segue(coin: coin)
                    }
                    .listRowBackground(Color.theme.background)
            }
        }
        .listStyle(PlainListStyle())
    }
    
    private var portfolioEmptyText: some View {
        Text("You haven't added any coins to your portfolio yet. Click the + button to get started! 🧐")
            .font(.callout)
            .foregroundColor(Color.theme.accent)
            .fontWeight(.medium)
            .multilineTextAlignment(.center)
            .padding(50)
    }
    
    private func segue(coin: CoinModel) {
        selectedCoin = coin
        showDetailView.toggle()
    }
    
    private var columnTitles: some View {
        HStack {
            HStack(spacing: 4) {
                Text("Coin")
                Image(systemName: "chevron.down")
                    .opacity((homeVM.sortOption == .rank || homeVM.sortOption == .rankReversed) ? 1.0 : 0.0)
                    .rotationEffect(Angle(degrees: homeVM.sortOption == .rank ? 0 : 180))
            }
            .onTapGesture {
                withAnimation(.default) {
                    homeVM.sortOption = homeVM.sortOption == .rank ? .rankReversed : .rankReversed
                }
            }
            Spacer()
            if showPortfolio {
                HStack(spacing: 4) {
                    Text("Holdings")
                    Image(systemName: "chevron.down")
                        .opacity((homeVM.sortOption == .holdings || homeVM.sortOption == .holdingsReversed) ? 1.0 : 0.0)
                        .rotationEffect(Angle(degrees: homeVM.sortOption == .holdings ? 0 : 180))
                }
                .onTapGesture {
                    withAnimation(.default) {
                        homeVM.sortOption = homeVM.sortOption == .holdings ? .holdingsReversed : .holdings
                    }
                }
            }
            
            HStack(spacing: 4) {
                Text("Price")
                Image(systemName: "chevron.down")
                    .opacity((homeVM.sortOption == .price || homeVM.sortOption == .priceReversed) ? 1.0 : 0.0)
                    .rotationEffect(Angle(degrees: homeVM.sortOption == .price ? 0 : 180))
            }
            .onTapGesture {
                withAnimation(.default) {
                    homeVM.sortOption = homeVM.sortOption == .price ? .priceReversed : .price
                }
            }
                .frame(width: UIScreen.main.bounds.width / 3.5, alignment: .trailing)
            Button {
                withAnimation(.linear(duration: 2.0)) {
                    homeVM.reloadData()
                }
            } label: {
                Image(systemName: "goforward")
            }
            .rotationEffect(Angle(degrees: homeVM.isLoading ? 360 : 0), anchor: .center)

        }
        .font(.caption)
        .foregroundColor(Color.theme.secondaryText)
        .padding(.horizontal)
    }
}

#Preview {
    HomeView()
}
