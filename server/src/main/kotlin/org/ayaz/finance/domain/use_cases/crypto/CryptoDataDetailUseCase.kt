package org.ayaz.finance.domain.use_cases.crypto

import org.ayaz.finance.data.repositories.crypto.ICryptoDataRepo

class CryptoDataDetailUseCase(
    private val repo: ICryptoDataRepo
) {
    suspend operator fun invoke(id: Int, convert: String) = repo.getDetailData(id, convert)
}