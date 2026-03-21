package org.ayaz.finance.data.repositories.crypto

import org.ayaz.finance.data.dto_s.crypto.CryptoQuotesResDTO
import org.ayaz.finance.data.dto_s.crypto.CryptoMapResDTO
import org.ayaz.finance.data.uow_s.crypto.ICryptoDataUow
import org.ayaz.finance.data.base.Response
import org.ayaz.finance.domain.base.Resource

interface ICryptoDataRepo {
    suspend fun getData(limit: Int, start: Int): Response<List<CryptoMapResDTO>>
    suspend fun getDetailData(id: Int, convert: String): Response<CryptoQuotesResDTO>
}

class CryptoDataRepo(
    private val cryptoDataUow: ICryptoDataUow
): ICryptoDataRepo {
    override suspend fun getData(limit: Int, start: Int): Response<List<CryptoMapResDTO>> {
        return when(val response = cryptoDataUow.getData(limit, start)) {
            is Resource.Error<List<CryptoMapResDTO>> -> Response.Error(code = response.code, errorMessages = response.messages)
            is Resource.Success<List<CryptoMapResDTO>> -> Response.Success(item = response.item)
        }
    }

    override suspend fun getDetailData(
        id: Int,
        convert: String
    ): Response<CryptoQuotesResDTO> {
        return when(val response = cryptoDataUow.getDetailData(id, convert)) {
            is Resource.Error<CryptoQuotesResDTO> -> Response.Error(code = response.code, errorMessages = response.messages)
            is Resource.Success<CryptoQuotesResDTO> -> Response.Success(item = response.item)
        }
    }

}