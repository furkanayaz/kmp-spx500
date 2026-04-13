package org.ayaz.exchange.presentation

import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain
import org.ayaz.exchange.presentation.plugins.installAuthentication
import org.ayaz.exchange.presentation.plugins.installContentNegotiation
import org.ayaz.exchange.presentation.plugins.installCors
import org.ayaz.exchange.presentation.plugins.installI18n
import org.ayaz.exchange.presentation.plugins.installKoin
import org.ayaz.exchange.presentation.plugins.installLogging
import org.ayaz.exchange.presentation.plugins.installRouting
import org.ayaz.exchange.presentation.plugins.installStatusPages
import org.ayaz.exchange.presentation.plugins.installApiDoc

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    installStatusPages()
    installApiDoc()
    installKoin()
    installCors()
    installLogging()
    installContentNegotiation()
    installAuthentication()
    installRouting()
    installI18n()
}