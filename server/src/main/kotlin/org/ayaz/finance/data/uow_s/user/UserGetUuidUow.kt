package org.ayaz.finance.data.uow_s.user

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.ayaz.finance.data.entities.user.UserEntity
import org.ayaz.finance.data.util.UserCollection
import org.litote.kmongo.findOne

fun interface IUserGetUuidUow {
    operator fun invoke(email: String): String?
}

class UserGetUuidUow(
    @UserCollection private val userCollection: MongoCollection<UserEntity>
): IUserGetUuidUow {
    override operator fun invoke(email: String): String? {
        val uuid = try {
            userCollection.findOne(Filters.eq(UserEntity::email.name, email))?.id ?: return null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        return uuid.toString()
    }
}