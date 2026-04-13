package org.ayaz.exchange.data.dto_s.crypto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.ayaz.exchange.data.util.serializers.CryptoDecimal

@Serializable
data class CryptoListResDTO<T: Any>(
    val data: List<T>,
    val status: CryptoResStatusDTO
) {
    fun isSuccess() = status.errorCode == 0
}

@Serializable
data class CryptoFilterResDTO<T: Any>(
    val data: Map<String, T>,
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
    @SerialName("circulating_supply") val circulatingSupply: CryptoDecimal,
    @SerialName("total_supply") val totalSupply: CryptoDecimal,
    @SerialName("max_supply") val maxSupply: CryptoDecimal,
    @SerialName("date_added") val addedDate: String?,
    @SerialName("last_updated") val lastUpdate: String?,
    val quote: Map<String, QuoteResDTO>?
)

@Serializable
data class QuoteResDTO(
    val price: CryptoDecimal,
    @SerialName("volume_24h") val volume24H: CryptoDecimal,
    @SerialName("percent_change_1h") val percentChange1H: CryptoDecimal,
    @SerialName("percent_change_24h") val percentChange24H: CryptoDecimal,
    @SerialName("percent_change_7d") val percentChange7D: CryptoDecimal,
    @SerialName("percent_change_30d") val percentChange30D: CryptoDecimal,
    @SerialName("percent_change_60d") val percentChange60D: CryptoDecimal,
    @SerialName("percent_change_90d") val percentChange90D: CryptoDecimal,
    @SerialName("market_cap") val marketCap: CryptoDecimal,
    @SerialName("last_updated") val lastUpdate: String?
)