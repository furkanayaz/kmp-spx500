package org.ayaz.exchange.domain.use_cases.spx

import org.ayaz.exchange.data.repositories.spx.ISpxDataRepo

class GetSpxDataUseCase(
    private val repo: ISpxDataRepo
) {
    operator fun invoke(pageNo: Int, pageSize: Int) = repo.getData(pageNo, pageSize)
}