package org.ayaz.spx500.data.repositories.user

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.ayaz.spx500.data.entities.user.UserEntity
import org.litote.kmongo.findOne

fun interface IUserValidationRepo {
    fun validate(email: String): Boolean
}

class UserValidationRepo(
    private val userCollection: MongoCollection<UserEntity>,
): IUserValidationRepo {
    override fun validate(email: String): Boolean {
        return userCollection.findOne { Filters.eq(UserEntity::email.name, email) } != null
    }
}