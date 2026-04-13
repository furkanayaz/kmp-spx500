package org.ayaz.exchange.data.uow_s.user

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.ayaz.exchange.data.entities.user.UserEntity

fun interface IUserValidationUow {
    fun validate(email: String): Boolean
}

class UserValidationUow(
    private val userCollection: MongoCollection<UserEntity>,
): IUserValidationUow {
    override fun validate(email: String): Boolean {
        return try {
            userCollection.find(Filters.eq(UserEntity::email.name, email)).singleOrNull() != null
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}