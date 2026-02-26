package org.ayaz.spx500.data.uow_s.user

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.ayaz.spx500.data.entities.user.UserEntity
import org.litote.kmongo.findOne

fun interface IUserGetUuidUow {
    operator fun invoke(email: String): String?
}

class UserGetUuidUow(
    private val userCollection: MongoCollection<UserEntity>
): IUserGetUuidUow {
    override operator fun invoke(email: String): String? {
        val uuid = userCollection.findOne(Filters.eq(UserEntity::email.name, email))?.id ?: return null
        return uuid.toString()
    }
}