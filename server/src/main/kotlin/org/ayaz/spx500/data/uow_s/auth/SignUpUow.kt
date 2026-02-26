package org.ayaz.spx500.data.uow_s.auth

import com.mongodb.MongoWriteException
import com.mongodb.client.MongoCollection
import org.ayaz.spx500.data.entities.user.UserEntity
import org.ayaz.spx500.data.dto_s.auth.SignUpReqDTO
import org.ayaz.spx500.domain.util.encryption.PasswordEncryption
import org.ayaz.spx500.domain.util.Resource

fun interface ISignUpUow {
    operator fun invoke(req: SignUpReqDTO): Resource<Boolean>
}

class SignUpUow(
    private val collection: MongoCollection<UserEntity>,
    private val passwordEncryption: PasswordEncryption
): ISignUpUow {
    private companion object {
        const val ERROR_MSG_USE_UNIQUE_EMAIL = "Please enter an unique email address."
        const val ERROR_MSG_UNKNOWN_ERROR = "Occurred an error while creating your account."
    }

    override operator fun invoke(req: SignUpReqDTO): Resource<Boolean> {
        val encryptedPassword = passwordEncryption.encode(req.password)
        val isUserRegistered = try {
            collection.insertOne(UserEntity(req.name, req.lastName, req.email, encryptedPassword.saltValue, encryptedPassword.encodedPassword))
        } catch (_: MongoWriteException) {
            return Resource.Error(listOf(ERROR_MSG_USE_UNIQUE_EMAIL))
        }.wasAcknowledged()

        return if (isUserRegistered) Resource.Success(true) else Resource.Error(listOf(ERROR_MSG_UNKNOWN_ERROR))
    }
}