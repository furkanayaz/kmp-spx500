package org.ayaz.exchange.data.auth.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import org.ayaz.exchange.data.uow_s.user.UserValidationUow
import java.util.Date
import kotlin.time.ExperimentalTime

class JWTUtil(
    private val userValidationRepo: UserValidationUow
) {
    private companion object {
        const val EMAIL = "email"
        const val EXPIRE_MILLIS = 1000 * 60 * 60 * 3 // 3 hours
    }

    @OptIn(ExperimentalTime::class)
    fun createToken(values: JWTValues, email: String): String =
        JWT.create()
            .withIssuer(values.issuer)
            .withAudience(values.audience)
            .withExpiresAt(Date(System.currentTimeMillis() + EXPIRE_MILLIS))
            .withClaim(EMAIL, email)
            .sign(Algorithm.HMAC256(values.secretKey))

    fun verifyToken(values: JWTValues): JWTVerifier =
        JWT.require(Algorithm.HMAC256(values.secretKey))
            .withIssuer(values.issuer)
            .withAudience(values.audience)
            .build()

    fun validateToken(credential: JWTCredential): JWTPrincipal? {
        val email = credential.payload.getClaim(EMAIL).asString()
        if (email.isNullOrEmpty() || userValidationRepo.validate(email).not()) return null

        return JWTPrincipal(credential.payload)
    }
}