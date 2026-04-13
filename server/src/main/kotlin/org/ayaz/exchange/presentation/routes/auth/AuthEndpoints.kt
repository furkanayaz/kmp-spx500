package org.ayaz.exchange.presentation.routes.auth

object AuthEndpoints {
    private const val BASE = "auth"
    const val LOGIN = "$BASE/login"
    const val SIGN_UP = "$BASE/signup"
    const val LOG_OUT = "$BASE/logout"
}