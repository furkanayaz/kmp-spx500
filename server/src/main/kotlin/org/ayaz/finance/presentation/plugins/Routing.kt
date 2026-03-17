package org.ayaz.finance.presentation.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.ayaz.finance.presentation.routes.auth.authRoutes
import org.ayaz.finance.presentation.routes.spx.spxRoutes

fun Application.installRouting() {
    routing {
        authRoutes()
        spxRoutes()
    }
}