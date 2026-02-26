package org.ayaz.spx500.presentation.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import jakarta.validation.ConstraintViolationException
import kotlinx.serialization.SerializationException
import org.ayaz.spx500.data.util.Response
import org.ayaz.spx500.presentation.util.SPXExceptions

fun Application.installStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, throwable ->
            when (throwable) {
                is SPXExceptions.MissingBodyException -> call.respond(HttpStatusCode.BadRequest, Response.Error<Any>(code = 400, errorMessages = listOf(throwable.message)))
                is SPXExceptions.MissingFieldException -> call.respond(HttpStatusCode.BadRequest, Response.Error<Any>(code = 400, errorMessages = listOf(throwable.message)))
                is SerializationException -> call.respond(HttpStatusCode.BadRequest, Response.Error<Any>(code = 400, errorMessages = listOf("Occurred a serialization error.")))
                is BadRequestException -> call.respond(HttpStatusCode.BadRequest, Response.Error<Any>(code = 400, errorMessages = listOf(throwable.message ?: "Your entered field(s) cannot null or empty.")))
                is ConstraintViolationException -> {
                    val fieldErrors = throwable.constraintViolations.map { it.propertyPath }.distinct().sortedBy { it.toString().first() }.joinToString()
                    val errorMessage = "Your entered $fieldErrors field(s) cannot be valid. Please review your information."
                    call.respond(HttpStatusCode.BadRequest, Response.Error<Any>(errorMessages = listOf(errorMessage)))
                }
                else -> call.respond(HttpStatusCode.InternalServerError, Response.Error<Any>(code = 500, errorMessages = listOf("Occurred an unknown exception in server.")))
            }
        }
    }
}