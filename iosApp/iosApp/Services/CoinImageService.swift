//
//  CoinImageService.swift
//  SwiftyCrypto
//
//  Created by Kyawt May Hlaing on 20/3/2566 BE.
//

import Foundation
import SwiftUI
import Combine
import Shared

class CoinImageService {
    @Published var image: UIImage?
    private var imageSubscription: AnyCancellable?
    private let coin: CoinModel
    private let fileManager = LocalFileManager.instance
    private let folderName = "coin_images"
    private lazy var imageName: String = {
        return coin.id
    }()
    
    init(coin: CoinModel) {
        self.coin = coin
        getCoinImage()
    }
    
    func getCoinImage() {
        if let savedImage = fileManager.getImage(imageName: coin.id, folderName: folderName) {
            image = savedImage
            print("Getting from file manager")
        } else {
            downloadCoinImage()
            print("Downloading")
        }
    }
    
    private func downloadCoinImage() {
        guard let url = URL(string: coin.image) else { return }
        
        imageSubscription = NetworkingManager.download(url: url)
            .tryMap({ data -> UIImage? in
                return UIImage(data: data)
            })
            .receive(on: DispatchQueue.main)
            .sink(receiveCompletion: NetworkingManager.handleCompletion, receiveValue: { [weak self] returnedImage in
                guard let self = self, let downloadedImage = returnedImage else { return }
                self.image = downloadedImage
                self.imageSubscription?.cancel()
                self.fileManager.saveImage(image: downloadedImage, imageName: self.imageName, folderName: self.folderName)
            })
    }
}
