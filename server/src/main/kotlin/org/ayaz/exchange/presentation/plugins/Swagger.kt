package org.ayaz.exchange.presentation.plugins

import io.ktor.openapi.OpenApiInfo
import io.ktor.server.application.Application
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.routing

fun Application.installApiDoc() {
    routing {
        /*openAPI(path = "openapi") {
            info = OpenApiInfo(title = "Finance API", description = "FURKAN AYAZ", version = "1.0.0")
        }*/
        swaggerUI(path = "swagger") {
            info = OpenApiInfo(title = "kmp-exchange API", description = "You can access all of the exchange api routes (Crypto and S&P 500). Also you can view the requests info for routes.", version = "1.0.0")
        }
    }
}