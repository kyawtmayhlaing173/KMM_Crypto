package com.pinky.kmm_crypto
import java.text.NumberFormat
import java.util.Locale

fun Double.toCurrencyFormat(
    locale: Locale = Locale.getDefault(),
): String {
    return NumberFormat.getCurrencyInstance(locale).apply {
        minimumIntegerDigits = 1
        isGroupingUsed = true
    }.format(this)
}