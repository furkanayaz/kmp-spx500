package org.ayaz.finance.presentation.plugins

import io.ktor.openapi.OpenApiInfo
import io.ktor.server.application.Application
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.routing

fun Application.installOpenAPI() {
    routing {
        openAPI(path = "openapi") {
            info = OpenApiInfo(title = "Finance API", description = "FURKAN AYAZ", version = "1.0.0")
        }
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
            version = "1.0.0"
        }
    }
}