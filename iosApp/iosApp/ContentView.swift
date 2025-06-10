import SwiftUI
import Shared

struct ContentView: View {
    @State private var showPortfolio: Bool = false
    @EnvironmentObject var homeVM: HomeViewModel
    @State private var showPortfolioView: Bool = false
    
//    @State private var selectedCoin: CoinModel?
    @State private var showDetailView: Bool = false
    @State private var showSettingsView: Bool = false
    
    @State private var showContent = false
    var body: some View {
        VStack {
            homeHeader
            if !showPortfolio {
                allCoinsList.transition(.move(edge: .leading))
                    .transition(.move(edge: .leading))
            }
        }
    }
}

extension ContentView {
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
//            PortfolioView()
//                .environmentObject(homeVM)
        }
    }
    
    private var allCoinsList: some View {
        List {
            ForEach(homeVM.allCoins, id: \.id) { coin in
                CoinRowView(coin: coin, showHoldingsColumn: false)
                    .onTapGesture {
//                        segue(coin: coin)
                    }
                    .listRowBackground(Color.theme.background)
            }
        }
        .listStyle(PlainListStyle())
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
