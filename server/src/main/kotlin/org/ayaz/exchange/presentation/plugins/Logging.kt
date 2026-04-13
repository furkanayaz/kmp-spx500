package org.ayaz.exchange.presentation.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.host
import io.ktor.server.application.install
import io.ktor.server.application.port
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import org.slf4j.event.Level

fun Application.installLogging() {
    install(CallLogging) {
        level = Level.DEBUG
        filter {
            val port = it.application.environment.config.port
            it.request.path().startsWith(port.toString())
        }
        format {
            val host = it.application.environment.config.host
            val port = it.application.environment.config.port

            val endpoint = it.request.path().substringAfterLast("/")
            val methodType = it.request.httpMethod.value
            val statusCode = it.response.status()?.value

            buildString {
                append("HOST: $host")
                append("PORT: $port")
                append("Endpoint: $endpoint")
                append("Method-Type: $methodType")
                append("Status-Code: $statusCode")
            }
        }
    }
}