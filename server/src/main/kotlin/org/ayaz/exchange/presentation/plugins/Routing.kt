package org.ayaz.exchange.presentation.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.ayaz.exchange.presentation.routes.auth.authRoutes
import org.ayaz.exchange.presentation.routes.crypto.cryptoRoutes
import org.ayaz.exchange.presentation.routes.spx500.spxRoutes

fun Application.installRouting() {
    routing {
        authRoutes()
        spxRoutes()
        cryptoRoutes()
    }
}