package org.ayaz.exchange.data.uow_s.auth

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import io.ktor.http.HttpStatusCode
import org.ayaz.exchange.data.entities.user.UserEntity
import org.ayaz.exchange.data.dto_s.auth.LoginReqDTO
import org.ayaz.exchange.data.util.UserCollection
import org.ayaz.exchange.domain.mapper.user.UserMapper
import org.ayaz.exchange.domain.models.user.UserModel
import org.ayaz.exchange.domain.util.encryption.PasswordEncryption
import org.ayaz.exchange.domain.base.Resource

fun interface ILoginUow {
    operator fun invoke(req: LoginReqDTO): Resource<UserModel>
}

class LoginUow(
    @UserCollection private val collection: MongoCollection<UserEntity>,
    private val passwordEncryption: PasswordEncryption,
    private val userMapper: UserMapper
): ILoginUow {
    override operator fun invoke(req: LoginReqDTO): Resource<UserModel> {
        val userSaltValue = collection.find(Filters.eq(UserEntity::email.name, req.email)).singleOrNull()?.salt ?: return Resource.Error(messages = listOf("enter.valid.email"))
        val encryptedPassword = passwordEncryption.encodeWithSalt(userSaltValue, req.password)
        val canUserLogin = try {
            collection.find(Filters.and(Filters.eq(UserEntity::email.name, req.email), Filters.eq(UserEntity::password.name, encryptedPassword))).singleOrNull() ?: return Resource.Error(messages = listOf("enter.valid.password"))
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(HttpStatusCode.InternalServerError.value, listOf(e.message.orEmpty()))
        }

        return Resource.Success(userMapper(canUserLogin))
    }
}