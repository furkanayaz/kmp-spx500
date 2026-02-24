package org.ayaz.spx500.data.di

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.IndexOptions
import org.ayaz.spx500.data.entities.user.UserEntity
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.litote.kmongo.KMongo
import org.litote.kmongo.ascending

@Module
class DBModule {
    private companion object {
        const val CONN_URL = "mongodb://localhost:27017"
        const val DB_NAME = "SPX500"

        const val USERS_COLLECTION = "Users"
    }

    @Single
    fun provideMongoDB(): MongoDatabase = KMongo.createClient(CONN_URL).getDatabase(DB_NAME)

    @Single
    fun provideUserCollection(mongoDatabase: MongoDatabase): MongoCollection<UserEntity> {
        val userCollection = mongoDatabase.getCollection(USERS_COLLECTION, UserEntity::class.java)
        userCollection.createIndex(ascending(UserEntity::email), IndexOptions().unique(true))

        return userCollection
    }
}