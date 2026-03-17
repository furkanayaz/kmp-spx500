package org.ayaz.finance.domain.use_cases.spx

import org.ayaz.finance.data.repositories.spx.ISpxDataRepo

class GetSpxDataDetailUseCase(
    private val repo: ISpxDataRepo
) {
    operator fun invoke(id: String) = repo.getDetailData(id)
}