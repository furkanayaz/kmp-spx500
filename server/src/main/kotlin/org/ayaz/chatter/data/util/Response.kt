package org.ayaz.bookstore.data.util

import kotlinx.serialization.Serializable

sealed interface Response {
    @Serializable
    data class Success<T: Any>(
        val isSuccess: Boolean = true,
        val code: Int = 200,
        val item: T? = null,
        val informationMessage: String? = null
    ): Response

    @Serializable
    data class Error(
        val isSuccess: Boolean = false,
        val errorCode: Int = 400,
        val errorMessages: List<String>
    ): Response
}