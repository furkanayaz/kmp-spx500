package org.ayaz.finance.data.di

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.IndexOptions
import org.ayaz.finance.data.entities.spx.SpxEntity
import org.ayaz.finance.data.entities.user.UserEntity
import org.ayaz.finance.data.util.SpxCollection
import org.ayaz.finance.data.util.UserCollection
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.litote.kmongo.KMongo
import org.litote.kmongo.ascending

@Module
class DBModule {
    private companion object {
        const val CONN_URL = "mongodb://localhost:27017"
        const val DB_NAME = "Finance"

        const val USERS_COLLECTION = "Users"
        const val SPX_COLLECTION = "SPX500"
        const val CRYPTO_COLLECTION = "Crypto"
    }

    @Single
    fun provideMongoDB(): MongoDatabase = KMongo.createClient(CONN_URL).getDatabase(DB_NAME)

    @Single
    @UserCollection
    fun provideUserCollection(db: MongoDatabase): MongoCollection<UserEntity> {
        val userCollection = db.getCollection(USERS_COLLECTION, UserEntity::class.java)
        userCollection.createIndex(ascending(UserEntity::email), IndexOptions().unique(true))

        return userCollection
    }

    @Single
    @SpxCollection
    fun provideSpxCollection(db: MongoDatabase): MongoCollection<SpxEntity> {
        val spxCollection = db.getCollection(SPX_COLLECTION, SpxEntity::class.java)
        return spxCollection
    }
}