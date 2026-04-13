package org.ayaz.exchange.data.entities.user

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import kotlin.time.Clock
import kotlin.time.Instant

data class UserEntity(
    val fistName: String,
    @BsonProperty("last_name")
    val lastName: String,
    val email: String,
    val salt: String,
    val password: String,
    val createdAt: Instant = Clock.System.now(),
    @BsonIgnore @Transient val token: String = "",
    @BsonId @BsonProperty("_id") val id: ObjectId = ObjectId()
)