package com.pinky.kmm_crypto.common

interface Destination {
    val title: String
    val route: String
    val routeWithArgs: String
}

object Home: Destination {
    override val title: String
        get() = "Coins"
    override val route: String
        get() = "home"
    override val routeWithArgs: String
        get() = route
}

object Setting: Destination {
    override val title: String
        get() = "Setting"
    override val route: String
        get() = "setting"
    override val routeWithArgs: String
        get() = route
}

object Portfolio: Destination {
    override val title: String
        get() = "Portfolio"
    override val route: String
        get() = "portfolio"
    override val routeWithArgs: String
        get() = route
}