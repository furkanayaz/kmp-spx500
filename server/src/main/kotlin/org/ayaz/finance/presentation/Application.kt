package org.ayaz.finance.presentation

import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain
import org.ayaz.finance.presentation.plugins.installAuthentication
import org.ayaz.finance.presentation.plugins.installContentNegotiation
import org.ayaz.finance.presentation.plugins.installCors
import org.ayaz.finance.presentation.plugins.installI18n
import org.ayaz.finance.presentation.plugins.installKoin
import org.ayaz.finance.presentation.plugins.installLogging
import org.ayaz.finance.presentation.plugins.installRouting
import org.ayaz.finance.presentation.plugins.installStatusPages
import org.ayaz.finance.presentation.plugins.installApiDoc

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