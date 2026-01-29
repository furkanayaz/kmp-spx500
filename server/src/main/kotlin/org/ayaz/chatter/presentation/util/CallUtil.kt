package org.ayaz.bookstore.presentation.util

import io.ktor.http.content.PartData
import io.ktor.server.application.ApplicationEnvironment
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receiveMultipart
import io.ktor.server.request.receiveNullable
import io.ktor.server.routing.RoutingCall
import io.ktor.utils.io.jvm.javaio.toInputStream
import org.ayaz.bookstore.data.util.jwt.JWTValues
import org.ayaz.bookstore.presentation.util.validations.Validator
import org.koin.ktor.ext.inject
import java.io.File

object CallUtil {
    fun ApplicationEnvironment.getJWTValues(): JWTValues {
        val secretKey = config.propertyOrNull(JWTValues.SECRET_KEY)?.getString().orEmpty()
        val issuer = config.propertyOrNull(JWTValues.ISSUER)?.getString().orEmpty()
        val audience = config.propertyOrNull(JWTValues.AUDIENCE)?.getString().orEmpty()

        return JWTValues(secretKey, issuer, audience)
    }

    suspend inline fun <reified T> RoutingCall.require(): T {
        val validator: Validator by inject()
        val body = try {
            this.receiveNullable<T>() ?: throw MessengerExceptions.MissingBodyException()
        } catch (_: BadRequestException) {
            throw MessengerExceptions.MissingFieldException()
        }

        validator.validate(body)

        return body
    }

    suspend fun RoutingCall.getSingleFile(): File {
        val partData = this.receiveMultipart().readPart() ?: throw MessengerExceptions.MissingBodyException()
        if (partData !is PartData.FileItem) throw MessengerExceptions.MissingBodyException()

        val fileBytes = partData.provider().toInputStream().readBytes()
        val fileName = partData.originalFileName ?: System.currentTimeMillis().toString()

        return File(fileName).also { it.writeBytes(fileBytes) }
    }
}