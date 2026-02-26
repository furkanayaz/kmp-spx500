package org.ayaz.spx500.presentation

import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain
import org.ayaz.spx500.presentation.plugins.installAuthentication
import org.ayaz.spx500.presentation.plugins.installContentNegotiation
import org.ayaz.spx500.presentation.plugins.installCors
import org.ayaz.spx500.presentation.plugins.installI18n
import org.ayaz.spx500.presentation.plugins.installKoin
import org.ayaz.spx500.presentation.plugins.installLogging
import org.ayaz.spx500.presentation.plugins.installRouting
import org.ayaz.spx500.presentation.plugins.installStatusPages
import org.ayaz.spx500.presentation.plugins.installOpenAPI

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    installStatusPages()
    installOpenAPI()
    installKoin()
    installCors()
    installLogging()
    installContentNegotiation()
    installAuthentication()
    installRouting()
    installI18n()
}