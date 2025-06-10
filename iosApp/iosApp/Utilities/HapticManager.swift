//
//  HapticManager.swift
//  SwiftyCrypto
//
//  Created by Kyawt May Hlaing on 9/4/2566 BE.
//

import Foundation
import SwiftUI

class HapticManager {
    static private let generator = UINotificationFeedbackGenerator()
    
    static func notification(type: UINotificationFeedbackGenerator.FeedbackType) {
        generator.notificationOccurred(type)
    }
}
