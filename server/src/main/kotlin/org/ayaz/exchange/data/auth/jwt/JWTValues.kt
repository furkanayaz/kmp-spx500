package org.ayaz.exchange.data.auth.jwt

data class JWTValues(
    val secretKey: String,
    val issuer: String,
    val audience: String
) {
    companion object {
        private const val BASE_PATH = "ktor.jwt"
        const val SECRET_KEY = "${BASE_PATH}.secretKey"
        const val ISSUER = "${BASE_PATH}.issuer"
        const val AUDIENCE = "${BASE_PATH}.audience"
    }
}