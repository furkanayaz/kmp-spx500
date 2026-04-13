package org.ayaz.exchange.data.di

import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.IndexOptions
import org.ayaz.exchange.data.entities.spx.SPXEntity
import org.ayaz.exchange.data.entities.spx.codec.SPXCodec
import org.ayaz.exchange.data.entities.spx.codec.SPXDetailCodec
import org.ayaz.exchange.data.entities.user.UserEntity
import org.ayaz.exchange.data.util.SpxCollection
import org.ayaz.exchange.data.util.UserCollection
import org.bson.codecs.configuration.CodecRegistries.fromCodecs
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.litote.kmongo.KMongo
import org.litote.kmongo.ascending
import org.litote.kmongo.withKMongo

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
    fun provideMongoDB(): MongoDatabase = KMongo.createClient(CONN_URL).getDatabase(DB_NAME).withKMongo()

    @Single
    @UserCollection
    fun provideUserCollection(db: MongoDatabase): MongoCollection<UserEntity> {
        val userCollection = db.getCollection(USERS_COLLECTION, UserEntity::class.java)
        userCollection.createIndex(ascending(UserEntity::email), IndexOptions().unique(true))

        return userCollection
    }

    @Single
    @SpxCollection
    fun provideSpxCollection(db: MongoDatabase): MongoCollection<SPXEntity> {
        val spxDetailCodec = SPXDetailCodec()
        val spxCodec = SPXCodec(spxDetailCodec)

        val spxCollection = db.getCollection(SPX_COLLECTION, SPXEntity::class.java).withCodecRegistry(fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromCodecs(spxCodec, spxDetailCodec)
        ))

        return spxCollection
    }
}