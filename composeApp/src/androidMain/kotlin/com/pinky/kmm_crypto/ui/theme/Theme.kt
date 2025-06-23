package com.pinky.kmm_crypto.ui.theme

import Blue50
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography.sectionTitle: TextStyle
    get() = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Gray
    )

val Typography.sectionSubtitle: TextStyle
    get() = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp
    )

val Typography.paragraph: TextStyle
    get() = TextStyle(
        fontSize = 14.sp,
        lineHeight = 24.sp
    )

val Typography.small: TextStyle
    get() = TextStyle(
        color = Color.Gray,
        fontSize = 12.sp,
        lineHeight = 24.sp
    )

val Typography.clickableText: TextStyle
    get() = TextStyle(
        color = Blue50,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )

val Typography.mainTitle: TextStyle
    get() = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold
    )