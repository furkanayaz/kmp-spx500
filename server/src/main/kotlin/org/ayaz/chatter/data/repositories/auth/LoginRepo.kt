package org.ayaz.bookstore.data.repositories.auth

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.ayaz.bookstore.data.entities.user.UserEntity
import org.ayaz.bookstore.data.dto_s.auth.LoginReqDTO
import org.ayaz.bookstore.domain.util.encryption.PasswordEncryption
import org.ayaz.bookstore.domain.util.Resource
import org.koin.core.annotation.Single
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

fun interface ILoginRepo {
    suspend fun login(req: LoginReqDTO): Resource<Boolean>
}

@Single
class LoginRepo(
    private val collection: MongoCollection<UserEntity>,
    private val passwordEncryption: PasswordEncryption
): ILoginRepo {
    private companion object {
        const val EMAIL_ADDRESS_ERROR = "Your entered email address cannot found."
        const val PASSWORD_ERROR = "Your entered password is wrong."
    }

    override suspend fun login(req: LoginReqDTO): Resource<Boolean> {
        val userSaltValue = collection.findOne(UserEntity::email eq req.email)?.salt ?: return Resource.Error(listOf(EMAIL_ADDRESS_ERROR))
        val encryptedPassword = passwordEncryption.encodeWithSalt(userSaltValue, req.password)
        val canUserLogin = collection.find(Filters.and(UserEntity::email eq req.email, UserEntity::password eq encryptedPassword)).singleOrNull()
        return if (canUserLogin != null) Resource.Success(true) else Resource.Error(listOf(PASSWORD_ERROR))
    }
}