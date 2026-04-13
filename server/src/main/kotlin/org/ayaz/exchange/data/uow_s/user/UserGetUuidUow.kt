package org.ayaz.exchange.data.uow_s.user

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.ayaz.exchange.data.entities.user.UserEntity
import org.ayaz.exchange.data.util.UserCollection

fun interface IUserGetUuidUow {
    operator fun invoke(email: String): String?
}

class UserGetUuidUow(
    @UserCollection private val userCollection: MongoCollection<UserEntity>
): IUserGetUuidUow {
    override operator fun invoke(email: String): String? {
        val uuid = try {
            userCollection.find(Filters.eq(UserEntity::email.name, email)).singleOrNull()?.id ?: return null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        return uuid.toString()
    }
}