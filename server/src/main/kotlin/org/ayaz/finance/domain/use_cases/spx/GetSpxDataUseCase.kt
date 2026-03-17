package org.ayaz.finance.domain.use_cases.spx

import org.ayaz.finance.data.repositories.spx.ISpxDataRepo

class GetSpxDataUseCase(
    private val repo: ISpxDataRepo
) {
    operator fun invoke(pageNo: Int, pageSize: Int) = repo.getData(pageNo, pageSize)
}