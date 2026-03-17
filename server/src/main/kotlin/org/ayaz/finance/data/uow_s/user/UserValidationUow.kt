package org.ayaz.finance.data.uow_s.user

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.ayaz.finance.data.entities.user.UserEntity
import org.litote.kmongo.findOne

fun interface IUserValidationUow {
    fun validate(email: String): Boolean
}

class UserValidationUow(
    private val userCollection: MongoCollection<UserEntity>,
): IUserValidationUow {
    override fun validate(email: String): Boolean {
        return try {
            userCollection.findOne { Filters.eq(UserEntity::email.name, email) } != null
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}