package org.ayaz.spx500.data.entities.spx

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class SpxEntity(
    val symbol: String,
    @BsonProperty("company_name") val companyName: String,
    val sector: String,
    @BsonProperty("date_added") val dateAdded: String,
    val headquarters: String,
    @BsonProperty("net_worth") val netWorth: String,
    @BsonId @BsonProperty("_id") val id: ObjectId = ObjectId()
)