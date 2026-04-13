package org.ayaz.exchange.data.entities.spx

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class SPXEntity(
    val symbol: String?,
    val security: String?,
    val sector: String?,
    val details: SPXDetailEntity?,
    @BsonId @BsonProperty("_id") val id: ObjectId
)

data class SPXDetailEntity(
    @BsonProperty("sub_industry") val subIndustry: String? = null,
    val headquarters: String? = null,
    @BsonProperty("date_added") val addedDate: String? = null,
    val cik: Int? = null,
    @BsonProperty("founded_date") val foundedDate: String? = null,
    @BsonProperty("net_worth") val netWorth: String? = null,
    val details: String? = null,
    @BsonProperty("details_tr") val detailsTr: String? = null
)