package org.ayaz.exchange.data.util

import org.koin.core.annotation.Qualifier

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention
@Qualifier
annotation class UserCollection

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention
@Qualifier
annotation class SpxCollection

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention
annotation class CoinMarketCap