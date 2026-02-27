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
    override operator fun invoke(req: SignUpReqDTO): Resource<Boolean> {
        val encryptedPassword = passwordEncryption.encode(req.password)
        val isUserRegistered = try {
            collection.insertOne(UserEntity(req.name, req.lastName, req.email, encryptedPassword.saltValue, encryptedPassword.encodedPassword))
        } catch (_: MongoWriteException) {
            return Resource.Error(listOf("enter.unique.email"))
        }.wasAcknowledged()

        return if (isUserRegistered) Resource.Success(true) else Resource.Error(listOf("account.not.create"))
    }
}