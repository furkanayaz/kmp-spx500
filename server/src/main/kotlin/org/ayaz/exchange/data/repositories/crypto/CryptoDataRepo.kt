package org.ayaz.exchange.data.repositories.crypto

import org.ayaz.exchange.data.dto_s.crypto.CryptoQuotesResDTO
import org.ayaz.exchange.data.dto_s.crypto.CryptoMapResDTO
import org.ayaz.exchange.data.uow_s.crypto.ICryptoDataUow
import org.ayaz.exchange.data.base.Response
import org.ayaz.exchange.data.repositories.logo.IExchangeLogoRepo
import org.ayaz.exchange.domain.base.Resource

interface ICryptoDataRepo {
    suspend fun getData(limit: Int, start: Int): Response<List<CryptoMapResDTO>>
    suspend fun getDetailData(id: Int, convert: String): Response<Map<String, CryptoQuotesResDTO>>
}

class CryptoDataRepo(
    private val cryptoDataUow: ICryptoDataUow,
    private val logoRepo: IExchangeLogoRepo
): ICryptoDataRepo {
    override suspend fun getData(limit: Int, start: Int): Response<List<CryptoMapResDTO>> {
        return when(val response = cryptoDataUow.getData(limit, start)) {
            is Resource.Error<List<CryptoMapResDTO>> -> Response.Error(code = response.code, errorMessages = response.messages)
            is Resource.Success<List<CryptoMapResDTO>> -> {
                val itemData = response.item.onEach { it.logoLink = logoRepo.getCryptoLogo(it.symbol) }
                Response.Success(item = itemData)
            }
        }
    }

    override suspend fun getDetailData(
        id: Int,
        convert: String
    ): Response<Map<String, CryptoQuotesResDTO>> {
        return when(val response = cryptoDataUow.getDetailData(id, convert)) {
            is Resource.Error<Map<String, CryptoQuotesResDTO>> -> Response.Error(code = response.code, errorMessages = response.messages)
            is Resource.Success<Map<String, CryptoQuotesResDTO>> -> Response.Success(item = response.item)
        }
    }

}