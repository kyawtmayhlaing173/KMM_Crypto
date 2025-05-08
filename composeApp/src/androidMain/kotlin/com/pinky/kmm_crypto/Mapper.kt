package com.pinky.kmm_crypto
import java.text.NumberFormat
import java.util.Locale

fun Double.toCurrencyFormat(
    locale: Locale = Locale.getDefault(),
    maxFractionDigits: Int = 2
): String {
    return NumberFormat.getCurrencyInstance(locale).apply {
        maximumIntegerDigits = maxFractionDigits
        minimumIntegerDigits = 1
        isGroupingUsed = true
    }.format(this)
}