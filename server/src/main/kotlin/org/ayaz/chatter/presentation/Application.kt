package org.ayaz.bookstore.presentation

import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.ayaz.bookstore.presentation.plugins.installAuthentication
import org.ayaz.bookstore.presentation.plugins.installContentNegotiation
import org.ayaz.bookstore.presentation.plugins.installCors
import org.ayaz.bookstore.presentation.plugins.installKoin
import org.ayaz.bookstore.presentation.plugins.installLogging
import org.ayaz.bookstore.presentation.plugins.installRouting
import org.ayaz.bookstore.presentation.plugins.installStatusPages

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    installStatusPages()
    installKoin()
    installCors()
    installLogging()
    installContentNegotiation()
    installAuthentication()
    installRouting()
}