package org.ayaz.exchange.presentation.util

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.i18n.i18n
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.request.header
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import org.ayaz.exchange.data.base.Response
import org.ayaz.exchange.data.base.Response.Success
import org.ayaz.exchange.data.auth.jwt.JWTValues
import org.ayaz.exchange.presentation.util.validations.Validator
import org.jetbrains.annotations.PropertyKey
import org.koin.ktor.ext.inject
import java.util.Locale

object CallUtil {
    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"

    fun ApplicationConfig.getJWTValues(): JWTValues {
        val secretKey = propertyOrNull(JWTValues.SECRET_KEY)?.getString().orEmpty()
        val issuer = propertyOrNull(JWTValues.ISSUER)?.getString().orEmpty()
        val audience = propertyOrNull(JWTValues.AUDIENCE)?.getString().orEmpty()

        return JWTValues(secretKey, issuer, audience)
    }

    fun RoutingCall.getClaim(): FinanceClaim {
        val email = this.principal<JWTPrincipal>()?.getClaim(KEY_EMAIL, String::class)
        val password = this.principal<JWTPrincipal>()?.getClaim(KEY_PASSWORD, String::class)

        return FinanceClaim(email, password)
    }

    fun RoutingCall.getLocale(): Locale {
        val acceptLanguage = request.header(HttpHeaders.AcceptLanguage)
        if (acceptLanguage == null) Locale.ENGLISH
        return Locale.of(acceptLanguage)
    }

    suspend fun RoutingCall.getPagingInfo(): PagingInfo {
        val pageNo = this.request.queryParameters["pageNo"]?.toIntOrNull()

        if (pageNo == null) this.sendErrorMessage("fields.required", "pageNo")

        val pageSize = this.request.queryParameters["pageSize"]?.toIntOrNull() ?: 10

        return PagingInfo(pageNo!!, pageSize)
    }

    suspend inline fun <reified T : Any> RoutingCall.require(): T {
        val validator: Validator by inject()
        val body = this.receive<T>()

        validator.validate(body)

        return body
    }

    suspend inline fun <reified T : Any> ApplicationCall.sendResponse(response: Response<T>) {
        when (response) {
            is Response.Error<T> -> this.respond(
                response.getHttpStatusCode(),
                response.copy(errorMessages = response.errorMessages.map { msgId -> i18n(msgId) })
            )

            is Success<T> -> this.respond(HttpStatusCode.OK, response)
        }
    }

    suspend fun ApplicationCall.sendErrorMessage(
        @PropertyKey(resourceBundle = "messages.messages") key: String,
        fieldName: String? = null,
        code: HttpStatusCode = HttpStatusCode.BadRequest
        ) {
        val errorMessage = if (fieldName?.isEmpty() == true) i18n(key) else String.format(i18n(key), fieldName)
        this.sendResponse(Response.Error(code = code.value, errorMessages = listOf(errorMessage)))
    }
}