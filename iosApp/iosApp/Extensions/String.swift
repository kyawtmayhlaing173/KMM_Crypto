//
//  String.swift
//  SwiftyCrypto
//
//  Created by Kyawt May Hlaing on 23/4/2566 BE.
//

import Foundation

extension String {
    var removingHTMLOccurances: String {
        return self.replacingOccurrences(of: "<[^>]+>", with: "", options: .regularExpression, range: nil)
    }
}
