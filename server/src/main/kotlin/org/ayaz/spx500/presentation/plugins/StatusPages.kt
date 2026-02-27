package org.ayaz.spx500.presentation.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.i18n.i18n
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import jakarta.validation.ConstraintViolationException
import kotlinx.serialization.SerializationException
import org.ayaz.spx500.data.util.Response
import org.ayaz.spx500.presentation.util.SPXExceptions
import java.text.MessageFormat

fun Application.installStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, throwable ->
            when (throwable) {
                is SPXExceptions.MissingBodyException -> call.respond(HttpStatusCode.BadRequest, Response.Error<Nothing>(code = 400, errorMessages = listOf(throwable.message)))
                is SPXExceptions.MissingFieldException -> call.respond(HttpStatusCode.BadRequest, Response.Error<Nothing>(code = 400, errorMessages = listOf(throwable.message)))
                is SerializationException -> call.respond(HttpStatusCode.BadRequest, Response.Error<Nothing>(code = 400, errorMessages = listOf(call.i18n("serialization.error"))))
                is BadRequestException -> call.respond(HttpStatusCode.BadRequest, Response.Error<Nothing>(code = 400, errorMessages = listOf(throwable.message ?: call.i18n("field.null.or.empty"))))
                is ConstraintViolationException -> {
                    val fieldErrors = throwable.constraintViolations.map { it.propertyPath }.distinct().sortedBy { it.toString().first() }.joinToString()
                    call.respond(HttpStatusCode.BadRequest, Response.Error<Nothing>(errorMessages = listOf(MessageFormat.format(call.i18n("field.not.valid"), fieldErrors))))
                }
                else -> call.respond(HttpStatusCode.InternalServerError, Response.Error<Nothing>(code = 500, errorMessages = listOf(call.i18n("unknown.error"))))
            }
        }
    }
}