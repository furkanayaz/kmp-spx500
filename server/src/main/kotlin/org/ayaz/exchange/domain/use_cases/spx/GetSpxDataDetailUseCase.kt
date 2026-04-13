package org.ayaz.exchange.domain.use_cases.spx

import org.ayaz.exchange.data.repositories.spx.ISpxDataRepo

class GetSpxDataDetailUseCase(
    private val repo: ISpxDataRepo
) {
    operator fun invoke(symbol: String) = repo.getDetailData(symbol)
}