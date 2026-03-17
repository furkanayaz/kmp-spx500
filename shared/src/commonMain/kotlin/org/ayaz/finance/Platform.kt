package org.ayaz.finance

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform