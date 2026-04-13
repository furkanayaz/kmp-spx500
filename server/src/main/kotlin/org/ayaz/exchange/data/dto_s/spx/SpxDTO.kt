package org.ayaz.exchange.data.dto_s.spx

import kotlinx.serialization.Serializable

@Serializable
data class SpxResDTO(
    val symbol: String?,
    val security: String?,
    val sector: String?,
    var logoLink: String?
)

@Serializable
data class SpxDetailResDTO(
    val subIndustry: String?,
    val headquarters: String?,
    val addedDate: String?,
    val cik: Int?,
    val foundedDate: String?,
    val netWorth: String?,
    val details: String?,
    val detailsTr: String?
)