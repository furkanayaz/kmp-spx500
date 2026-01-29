package org.ayaz.bookstore.domain.util.encryption

data class EncryptedPassword(
    val saltValue: String,
    val encodedPassword: String
)
