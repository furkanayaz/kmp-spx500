package org.ayaz.spx500.data.util.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import org.ayaz.spx500.data.repositories.user.UserValidationRepo
import java.util.Date
import kotlin.time.ExperimentalTime

class JWTUtil(
    private val userValidationRepo: UserValidationRepo
) {
    private companion object {
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val EXPIRE_MILLIS = 1000 * 60 * 60 * 3 // 3 hours
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
        val email = credential.payload.getClaim(EMAIL).asString()
        val password = credential.payload.getClaim(PASSWORD).asString()
        if (email.isNullOrEmpty() && password.isNullOrEmpty() && userValidationRepo.validate(email).not()) return null

        return JWTPrincipal(credential.payload)
    }
}