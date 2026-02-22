package org.ayaz.spx500.domain.util

sealed interface Resource<T> where T: Any {
    data class Error<T: Any>(val messages: List<String>): Resource<T>
    data class Success<T: Any>(val item: T): Resource<T>
}