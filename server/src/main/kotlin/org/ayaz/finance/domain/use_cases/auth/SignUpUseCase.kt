package org.ayaz.finance.domain.use_cases.auth

import org.ayaz.finance.data.dto_s.auth.SignUpReqDTO
import org.ayaz.finance.data.repositories.auth.ISignUpRepo

class SignUpUseCase(
    private val repo: ISignUpRepo
) {
    operator fun invoke(req: SignUpReqDTO) = repo(req)
}