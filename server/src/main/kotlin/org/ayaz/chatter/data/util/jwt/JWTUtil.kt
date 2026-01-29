package org.ayaz.bookstore.data.util.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import org.koin.core.annotation.Single
import java.util.Date
import kotlin.time.ExperimentalTime

@Single
class JWTUtil {
    private companion object {
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val EXPIRE_MILLIS = 1000 * 60 * 60 * 24
    }

    @OptIn(ExperimentalTime::class)
    fun createToken(values: JWTValues, email: String, password: String): String =
        JWT.create()
            .withIssuer(values.issuer)
            .withAudience(values.audience)
            .withExpiresAt(Date(System.currentTimeMillis() + EXPIRE_MILLIS))
            .withClaim(EMAIL, email)
            .withClaim(PASSWORD, password)
            .sign(Algorithm.HMAC256(values.secretKey))

    fun verifyToken(values: JWTValues): JWTVerifier =
        JWT.require(Algorithm.HMAC256(values.secretKey))
            .withIssuer(values.issuer)
            .withAudience(values.audience)
            .build()

    fun validateToken(credential: JWTCredential): JWTPrincipal? {
        val isEmailNullOrEmpty = credential.payload.getClaim(EMAIL).asString().isNullOrEmpty()
        val isPasswordNullOrEmpty = credential.payload.getClaim(PASSWORD).asString().isNullOrEmpty()
        return if (isEmailNullOrEmpty && isPasswordNullOrEmpty) {
            null
        } else {
            JWTPrincipal(credential.payload)
        }
    }
}