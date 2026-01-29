package org.ayaz.bookstore.presentation.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.ayaz.bookstore.presentation.routes.auth.authRoutes

fun Application.installRouting() {
    routing {
        authRoutes()
    }
}