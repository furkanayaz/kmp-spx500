package org.ayaz.exchange.data.uow_s.auth

import com.mongodb.MongoWriteException
import com.mongodb.client.MongoCollection
import io.ktor.http.HttpStatusCode
import org.ayaz.exchange.data.entities.user.UserEntity
import org.ayaz.exchange.data.dto_s.auth.SignUpReqDTO
import org.ayaz.exchange.data.util.UserCollection
import org.ayaz.exchange.domain.util.encryption.PasswordEncryption
import org.ayaz.exchange.domain.base.Resource

fun interface ISignUpUow {
    operator fun invoke(req: SignUpReqDTO): Resource<Boolean>
}

class SignUpUow(
    @UserCollection private val collection: MongoCollection<UserEntity>,
    private val passwordEncryption: PasswordEncryption
): ISignUpUow {
    override operator fun invoke(req: SignUpReqDTO): Resource<Boolean> {
        val encryptedPassword = passwordEncryption.encode(req.password)
        val isUserRegistered = try {
            collection.insertOne(UserEntity(req.name, req.lastName, req.email, encryptedPassword.saltValue, encryptedPassword.encodedPassword))
        } catch (_: MongoWriteException) {
            return Resource.Error(HttpStatusCode.InternalServerError.value, listOf("enter.unique.email"))
        }.wasAcknowledged()

        return if (isUserRegistered) Resource.Success(true) else Resource.Error(messages = listOf("account.not.create"))
    }
}