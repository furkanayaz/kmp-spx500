package org.ayaz.spx500.data.uow_s.user

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.ayaz.spx500.data.entities.user.UserEntity
import org.litote.kmongo.findOne

fun interface IUserValidationUow {
    fun validate(email: String): Boolean
}

class UserValidationUow(
    private val userCollection: MongoCollection<UserEntity>,
): IUserValidationUow {
    override fun validate(email: String): Boolean {
        return userCollection.findOne { Filters.eq(UserEntity::email.name, email) } != null
    }
}