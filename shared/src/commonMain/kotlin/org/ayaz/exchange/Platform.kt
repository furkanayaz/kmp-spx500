package org.ayaz.exchange

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform