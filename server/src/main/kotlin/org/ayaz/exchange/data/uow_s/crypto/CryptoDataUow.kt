package org.ayaz.exchange.data.uow_s.crypto

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.util.appendAll
import org.ayaz.exchange.data.dto_s.crypto.CryptoListResDTO
import org.ayaz.exchange.data.dto_s.crypto.CryptoQuotesResDTO
import org.ayaz.exchange.data.dto_s.crypto.CryptoMapResDTO
import org.ayaz.exchange.data.dto_s.crypto.CryptoFilterResDTO
import org.ayaz.exchange.domain.base.Resource

interface ICryptoDataUow {
    suspend fun getData(limit: Int, start: Int): Resource<List<CryptoMapResDTO>>
    suspend fun getDetailData(id: Int, convert: String): Resource<Map<String, CryptoQuotesResDTO>>
}

class CryptoDataUow(
    private val client: HttpClient
) : ICryptoDataUow {
    override suspend fun getData(limit: Int, start: Int): Resource<List<CryptoMapResDTO>> {
        return try {
            val response = client.get("/v1/cryptocurrency/map") {
                url.parameters.appendAll(mapOf("limit" to limit.toString(), "start" to (start * 2 + 1).toString()))
            }.body<CryptoListResDTO<CryptoMapResDTO>>()

            if (response.isSuccess()) Resource.Success(response.data) else Resource.Error(response.status.errorCode, listOf(response.status.getErrorMessage()))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(HttpStatusCode.InternalServerError.value, listOf(e.message.orEmpty()))
        }
    }

    override suspend fun getDetailData(id: Int, convert: String): Resource<Map<String, CryptoQuotesResDTO>> {
        return try {
            val response = client.get("/v1/cryptocurrency/quotes/latest") {
                url.parameters.appendAll(mapOf("id" to id.toString(), "convert" to convert))
            }.body<CryptoFilterResDTO<CryptoQuotesResDTO>>()

            if (response.isSuccess()) Resource.Success(response.data) else Resource.Error(response.status.errorCode, listOf(response.status.getErrorMessage()))
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(HttpStatusCode.InternalServerError.value, listOf(e.message.orEmpty()))
        }
    }

}