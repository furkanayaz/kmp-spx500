package org.ayaz.exchange.domain.util

fun interface Mapper<in T: Any, out S: Any> {
    operator fun invoke(dto: T): S
}