package org.ayaz.bookstore.domain.util

fun interface Mapper<in T: Any, out S: Any> {
    fun toModel(dto: T): S
}