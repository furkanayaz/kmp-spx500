package org.ayaz.bookstore

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform