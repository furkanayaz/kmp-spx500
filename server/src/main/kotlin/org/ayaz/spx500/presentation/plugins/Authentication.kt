package org.ayaz.spx500.presentation.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import org.ayaz.spx500.data.util.Response
import org.ayaz.spx500.data.util.jwt.JWTUtil
import org.ayaz.spx500.presentation.util.CallUtil.getJWTValues
import org.koin.ktor.ext.inject

fun Application.installAuthentication() {
    val jwtUtil: JWTUtil by inject()
    val jwtValues = environment.config.getJWTValues()

    install(Authentication) {
        jwt {
            verifier { jwtUtil.verifyToken(jwtValues) }
            validate { jwtUtil.validateToken(it) }
            challenge { _, _ -> call.respond(HttpStatusCode.Unauthorized, Response.Error<Any>(code = 401, errorMessages = listOf("Token is not valid or has expired."))) }
        }
    }
}