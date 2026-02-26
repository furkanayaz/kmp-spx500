package org.ayaz.spx500.data.uow_s.auth

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

fun interface ILoginUow {
    operator fun invoke(req: LoginReqDTO): Resource<UserModel>
}

class LoginUow(
    private val collection: MongoCollection<UserEntity>,
    private val passwordEncryption: PasswordEncryption,
    private val userMapper: UserMapper
): ILoginUow {
    override operator fun invoke(req: LoginReqDTO): Resource<UserModel> {
        val userSaltValue = collection.findOne(Filters.eq(UserEntity::email.name, req.email))?.salt ?: return Resource.Error(listOf("enter.valid.email"))
        val encryptedPassword = passwordEncryption.encodeWithSalt(userSaltValue, req.password)
        val canUserLogin = collection.find(Filters.and(UserEntity::email eq req.email, UserEntity::password eq encryptedPassword)).singleOrNull() ?: return Resource.Error(listOf("enter.valid.password"))

        return Resource.Success(userMapper.toModel(canUserLogin))
    }
}