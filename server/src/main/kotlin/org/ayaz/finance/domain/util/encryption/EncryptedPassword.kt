package org.ayaz.finance.domain.util.encryption

data class EncryptedPassword(
    val saltValue: String,
    val encodedPassword: String
)
