package org.ayaz.spx500.presentation.plugins

import io.ktor.openapi.OpenApiInfo
import io.ktor.server.application.Application
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.routing

fun Application.installOpenAPI() {
    routing {
        openAPI(path = "openapi") {
            info = OpenApiInfo(title = "SPX500 API", description = "FURKAN AYAZ", version = "1.0.0")
        }
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
            version = "1.0.0"
        }
    }
}