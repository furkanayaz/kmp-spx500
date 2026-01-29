package org.ayaz.bookstore.domain.util

sealed interface Resource<T> where T: Any {
    data class Error<T: Any>(val messages: List<String>): Resource<T>
    data class Success<T: Any>(val item: T? = null): Resource<T>
}