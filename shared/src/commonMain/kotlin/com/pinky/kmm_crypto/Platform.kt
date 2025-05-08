package com.pinky.kmm_crypto

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform