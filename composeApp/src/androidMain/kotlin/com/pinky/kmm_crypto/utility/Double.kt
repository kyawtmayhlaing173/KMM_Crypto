package com.pinky.kmm_crypto.utility

import kotlin.math.abs

fun Double.formatWithAbbreviations(): String {
    val num = abs(this)
    val sign = if (this < 0) "-" else ""

    return when {
        num >= 1_000_000_000_000 -> {
            val formatted = num / 1_000_000_000_000
            "$sign${formatted.asNumberString()}Tr"
        }
        num >= 1_000_000_000 -> {
            val formatted = num / 1_000_000_000
            "$sign${formatted.asNumberString()}Bn"
        }
        num >= 1_000_000 -> {
            val formatted = num / 1_000_000
            "$sign${formatted.asNumberString()}M"
        }
        num >= 1_000 -> {
            val formatted = num / 1_000
            "$sign${formatted.asNumberString()}K"
        }
        else -> {
            this.asNumberString()
        }
    }
}

fun Double.asNumberString(): String {
    return if (this % 1 == 0.0) {
        String.format("%.0f", this)
    } else {
        String.format("%.1f", this)
    }
}