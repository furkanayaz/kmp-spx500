package org.ayaz.finance.presentation.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.i18n.i18n
import io.ktor.i18n.locale
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import jakarta.validation.MessageInterpolator
import jakarta.validation.Validation
import jakarta.validation.metadata.ConstraintDescriptor
import kotlinx.serialization.SerializationException
import org.ayaz.finance.data.util.Response
import org.ayaz.finance.presentation.util.FinanceExceptions
import java.util.Locale

fun Application.installStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, throwable ->
            when (throwable) {
                is FinanceExceptions.MissingBodyException -> call.respond(HttpStatusCode.BadRequest, Response.Error<Nothing>(code = 400, errorMessages = listOf(throwable.message)))
                is FinanceExceptions.MissingFieldException -> call.respond(HttpStatusCode.BadRequest, Response.Error<Nothing>(code = 400, errorMessages = listOf(throwable.message)))
                is SerializationException -> call.respond(HttpStatusCode.BadRequest, Response.Error<Nothing>(code = 400, errorMessages = listOf(call.i18n("serialization.error"))))
                is BadRequestException -> call.respond(HttpStatusCode.BadRequest, Response.Error<Nothing>(code = 400, errorMessages = listOf(throwable.message ?: call.i18n("field.null.or.empty"))))
                is ConstraintViolationException -> {
                    val errorMessages = throwable.constraintViolations.map {
                        val fieldName = it.propertyPath.last().name.replaceFirstChar { first -> first.titlecase() }
                        val translatedMessage = if (it.message == "password.not.valid") call.i18n("password.not.valid") else it.getLocalizedMessage(call.locale)
                        "$fieldName: $translatedMessage"
                    }

                    call.respond(HttpStatusCode.BadRequest, Response.Error<Nothing>(errorMessages = errorMessages))
                }
                else -> call.respond(HttpStatusCode.InternalServerError, Response.Error<Nothing>(code = 500, errorMessages = listOf(call.i18n("unknown.error"))))
            }
        }
    }
}

private fun ConstraintViolation<*>.getLocalizedMessage(locale: Locale): String {
    val interpolator = Validation.byDefaultProvider().configure().defaultMessageInterpolator
    return interpolator.interpolate(messageTemplate, object : MessageInterpolator.Context {
        override fun getConstraintDescriptor(): ConstraintDescriptor<*>? = this@getLocalizedMessage.constraintDescriptor
        override fun getValidatedValue(): Any? = invalidValue
        override fun <T : Any?> unwrap(type: Class<T?>?): T? = null

    }, locale)
}