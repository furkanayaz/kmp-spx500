package org.ayaz.spx500.data.repositories.auth

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.ayaz.spx500.data.entities.user.UserEntity
import org.ayaz.spx500.data.dto_s.auth.LoginReqDTO
import org.ayaz.spx500.domain.mapper.user.UserMapper
import org.ayaz.spx500.domain.models.user.UserModel
import org.ayaz.spx500.domain.util.encryption.PasswordEncryption
import org.ayaz.spx500.domain.util.Resource
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

fun interface ILoginRepo {
    suspend fun login(req: LoginReqDTO): Resource<UserModel>
}

class LoginRepo(
    private val collection: MongoCollection<UserEntity>,
    private val passwordEncryption: PasswordEncryption,
    private val userMapper: UserMapper
): ILoginRepo {
    private companion object {
        const val EMAIL_ADDRESS_ERROR = "Your entered email address cannot found."
        const val PASSWORD_ERROR = "Your entered password is wrong."
    }

    override suspend fun login(req: LoginReqDTO): Resource<UserModel> {
        val userSaltValue = collection.findOne(UserEntity::email eq req.email)?.salt ?: return Resource.Error(listOf(EMAIL_ADDRESS_ERROR))
        val encryptedPassword = passwordEncryption.encodeWithSalt(userSaltValue, req.password)
        val canUserLogin = collection.find(Filters.and(UserEntity::email eq req.email, UserEntity::password eq encryptedPassword)).singleOrNull() ?: return Resource.Error(listOf(PASSWORD_ERROR))

        return Resource.Success(userMapper.toModel(canUserLogin))
    }
}