package org.ayaz.spx500.domain.use_cases.auth

import org.ayaz.spx500.data.dto_s.auth.SignUpReqDTO
import org.ayaz.spx500.data.repositories.auth.ISignUpRepo

class SignUpUseCase(
    private val repo: ISignUpRepo
) {
    operator fun invoke(req: SignUpReqDTO) = repo(req)
}