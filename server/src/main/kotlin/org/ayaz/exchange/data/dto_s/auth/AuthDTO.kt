package org.ayaz.exchange.data.dto_s.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.ayaz.exchange.presentation.util.validations.annotations.Password

@Serializable
data class LoginReqDTO(
    @Email
    val email: String,
    @field:Password
    val password: String
)

@Serializable
data class LoginResDTO(
    val name: String,
    val lastName: String,
    val token: String,
    val createdAt: LocalDateTime
)

@Serializable
data class SignUpReqDTO(
    @Size(min = 2)
    val name: String,
    @Size(min = 2)
    val lastName: String,
    @Email
    val email: String,
    @field:Password
    val password: String
)