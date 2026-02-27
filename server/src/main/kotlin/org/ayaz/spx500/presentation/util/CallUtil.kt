package org.ayaz.spx500.presentation.util

import io.ktor.http.HttpStatusCode
import io.ktor.i18n.i18n
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import org.ayaz.spx500.data.util.Response
import org.ayaz.spx500.data.util.Response.Success
import org.ayaz.spx500.data.util.jwt.JWTValues
import org.ayaz.spx500.data.util.server.SPXServer
import org.ayaz.spx500.presentation.util.validations.Validator
import org.koin.ktor.ext.inject

object CallUtil {
    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"

    fun ApplicationConfig.getSPXServer(): SPXServer {
        val host = propertyOrNull(SPXServer.HOST)?.getString() ?: SPXServer.DEFAULT_HOST
        val port = propertyOrNull(SPXServer.PORT)?.getString()?.toIntOrNull() ?: SPXServer.DEFAULT_PORT

        return SPXServer(host, port)
    }

    fun ApplicationConfig.getJWTValues(): JWTValues {
        val secretKey = propertyOrNull(JWTValues.SECRET_KEY)?.getString().orEmpty()
        val issuer = propertyOrNull(JWTValues.ISSUER)?.getString().orEmpty()
        val audience = propertyOrNull(JWTValues.AUDIENCE)?.getString().orEmpty()

        return JWTValues(secretKey, issuer, audience)
    }

    fun RoutingCall.getClaim(): SPXClaim {
        val email = this.principal<JWTPrincipal>()?.getClaim(KEY_EMAIL, String::class)
        val password = this.principal<JWTPrincipal>()?.getClaim(KEY_PASSWORD, String::class)

        return SPXClaim(email, password)
    }

    suspend inline fun <reified T> RoutingCall.require(): T {
        val validator: Validator by inject()
        val body = try {
            this.receiveNullable<T>() ?: throw SPXExceptions.MissingBodyException()
        } catch (_: BadRequestException) {
            throw SPXExceptions.MissingFieldException()
        }

        validator.validate(body)

        return body
    }

    suspend inline fun <reified T: Any> ApplicationCall.sendResponse(response: Response<T>) {
        when(response) {
            is Response.Error<T> -> this.respond(response.getHttpStatusCode(), response.copy(errorMessages = response.errorMessages.map { msgId -> i18n(msgId) }))
            is Success<T> -> this.respond(HttpStatusCode.OK, response)
        }
    }
}