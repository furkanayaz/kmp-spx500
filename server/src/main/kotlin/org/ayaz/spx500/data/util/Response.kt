package org.ayaz.spx500.data.util

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

sealed interface Response<T: Any> {
    @Serializable
    data class Success<T: Any>(
        val isSuccess: Boolean = true,
        val code: Int = 200,
        val item: T? = null,
        val informationMessage: String? = null
    ): Response<T>

    @Serializable
    data class Error<T: Any>(
        val isSuccess: Boolean = false,
        val code: Int = 400,
        val errorMessages: List<String>,
        private val item: T? = null,
    ): Response<T> {

        fun getHttpStatusCode(): HttpStatusCode {
            return when(code) {
                400 -> HttpStatusCode.BadRequest
                500 -> HttpStatusCode.InternalServerError
                else -> HttpStatusCode.InternalServerError
            }
        }

    }
}