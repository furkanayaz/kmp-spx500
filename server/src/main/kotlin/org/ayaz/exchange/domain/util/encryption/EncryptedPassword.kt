package org.ayaz.exchange.domain.util.encryption

data class EncryptedPassword(
    val saltValue: String,
    val encodedPassword: String
)
