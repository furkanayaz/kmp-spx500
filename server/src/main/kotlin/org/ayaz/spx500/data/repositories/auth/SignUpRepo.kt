package org.ayaz.spx500.data.repositories.auth

import com.mongodb.MongoWriteException
import com.mongodb.client.MongoCollection
import org.ayaz.spx500.data.entities.user.UserEntity
import org.ayaz.spx500.data.dto_s.auth.SignUpReqDTO
import org.ayaz.spx500.domain.util.encryption.PasswordEncryption
import org.ayaz.spx500.domain.util.Resource

fun interface ISignUpRepo {
    fun signUp(req: SignUpReqDTO): Resource<Boolean>
}

class SignUpRepo(
    private val collection: MongoCollection<UserEntity>,
    private val passwordEncryption: PasswordEncryption
): ISignUpRepo {
    override fun signUp(req: SignUpReqDTO): Resource<Boolean> {
        val encryptedPassword = passwordEncryption.encode(req.password)
        val isUserRegistered = try {
            collection.insertOne(UserEntity(req.name, req.lastName, req.email, encryptedPassword.saltValue, encryptedPassword.encodedPassword))
        } catch (_: MongoWriteException) {
            return Resource.Error(listOf("Please enter an unique email address."))
        }.wasAcknowledged()

        return if (isUserRegistered) Resource.Success(true) else Resource.Error(listOf("Occurred an error while creating your account."))
    }
}