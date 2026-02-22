package org.ayaz.spx500.presentation.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.ayaz.spx500.presentation.routes.auth.authRoutes
import org.ayaz.spx500.presentation.routes.spx.spxRoutes

fun Application.installRouting() {
    routing {
        authRoutes()
        spxRoutes()
    }
}