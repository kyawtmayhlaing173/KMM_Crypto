//
//  CircleButtonView.swift
//  SwiftyCrypto
//
//  Created by Kyawt May Hlaing on 2/3/2566 BE.
//

import SwiftUI

struct CircleButtonView: View {
    let iconImage: String
    
    var body: some View {
        Image(systemName: iconImage)
            .font(.headline)
            .foregroundColor(Color.theme.accent)
            .frame(width: 50, height: 50)
            .background(
                Circle()
                    .foregroundColor(Color.theme.background)
            )
            .shadow(color: Color.theme.accent.opacity(0.25), radius: 10, x: 0, y: 0)
            .padding()
    }
}

struct CircleButtonView_Previews: PreviewProvider {
    static var imageString = "heart.fill"
    static var previews: some View {
        Group {
            CircleButtonView(iconImage: imageString)
                .padding()
                .previewLayout(.sizeThatFits)
                .preferredColorScheme(.dark)
            CircleButtonView(iconImage: imageString)
                .padding()
                .previewLayout(.sizeThatFits)
                .preferredColorScheme(.light)
        }
    }
}
