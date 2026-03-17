package org.ayaz.finance.data.util

import org.koin.core.annotation.Qualifier

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention
@Qualifier
annotation class UserCollection

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention
@Qualifier
annotation class SpxCollection