package org.ayaz.finance.data.dto_s.crypto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoListResDTO<T: Any>(
    val data: List<T>,
    val status: CryptoResStatusDTO
) {
    fun isSuccess() = status.errorCode == 0
}

@Serializable
data class CryptoResDTO<T: Any>(
    val data: T,
    val status: CryptoResStatusDTO
) {
    fun isSuccess() = status.errorCode == 0
}

@Serializable
data class CryptoResStatusDTO(
    @SerialName("error_code") val errorCode: Int,
    @SerialName("error_message") private val errorMessage: String? = null
) {
    fun getErrorMessage(): String = errorMessage ?: "Crypto - empty error message."
}

@Serializable
data class CryptoMapResDTO(
    val id: Int? = null,
    val name: String? = null,
    val symbol: String? = null,
    val slug: String? = null
)

@Serializable
data class CryptoQuotesResDTO(
    @SerialName("infinite_supply") val infiniteSupply: Boolean?,
    @SerialName("circulating_supply") val circulatingSupply: Double?,
    @SerialName("total_supply") val totalSupply: Double?,
    @SerialName("max_supply") val maxSupply: Double?,
    @SerialName("date_added") val addedDate: String?,
    @SerialName("last_updated") val lastUpdate: String?,
    val usd: QuoteResDTO? = null,
    @SerialName("TRY") val trY: QuoteResDTO? = null,
    val tags: List<String>?
)

@Serializable
data class QuoteResDTO(
    val price: Double?,
    @SerialName("volume_24h") val volume24H: Double?,
    @SerialName("percent_change_1h") val percentChange1H: Double?,
    @SerialName("percent_change_24h") val percentChange24H: Double?,
    @SerialName("percent_change_7d") val percentChange7D: Double?,
    @SerialName("percent_change_30d") val percentChange30D: Double?,
    @SerialName("percent_change_60d") val percentChange60D: Double?,
    @SerialName("percent_change_90d") val percentChange90D: Double?,
    @SerialName("market_cap") val marketCap: Double?,
    @SerialName("last_updated") val lastUpdate: String?
)