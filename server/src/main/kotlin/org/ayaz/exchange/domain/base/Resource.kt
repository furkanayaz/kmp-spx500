package org.ayaz.exchange.domain.base

import io.ktor.http.HttpStatusCode

sealed interface Resource<T> where T: Any {
    data class Error<T: Any>(val code: Int = HttpStatusCode.BadRequest.value, val messages: List<String>): Resource<T>
    data class Success<T: Any>(val item: T): Resource<T>
}