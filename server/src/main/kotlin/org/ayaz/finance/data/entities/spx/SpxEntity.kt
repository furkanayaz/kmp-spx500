package org.ayaz.finance.data.entities.spx

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class SpxEntity(
    val symbol: String,
    val security: String,
    val sector: String,
    @BsonProperty("sub_industry") val subIndustry: String,
    val headquarters: String,
    @BsonProperty("date_added") val addedDate: String,
    val cik: Int,
    @BsonProperty("founded_date") val foundedDate: String,
    @BsonProperty("net_worth") val netWorth: String,
    val details: String,
    @BsonProperty("details_tr") val detailsTr: String,
    @BsonId @BsonProperty("_id") val id: ObjectId
)