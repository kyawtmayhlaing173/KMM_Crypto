//
//  UIApplication.swift
//  SwiftyCrypto
//
//  Created by Kyawt May Hlaing on 22/3/2566 BE.
//

import Foundation
import SwiftUI

extension UIApplication {
    func endEditing() {
        sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}
