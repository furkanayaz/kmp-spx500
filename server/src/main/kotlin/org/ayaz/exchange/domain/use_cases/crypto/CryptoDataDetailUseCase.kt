package org.ayaz.exchange.domain.use_cases.crypto

import org.ayaz.exchange.data.repositories.crypto.ICryptoDataRepo

class CryptoDataDetailUseCase(
    private val repo: ICryptoDataRepo
) {
    suspend operator fun invoke(id: Int, convert: String) = repo.getDetailData(id, convert)
}