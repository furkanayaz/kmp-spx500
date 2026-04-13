package org.ayaz.exchange.domain.use_cases.auth

import org.ayaz.exchange.data.dto_s.auth.SignUpReqDTO
import org.ayaz.exchange.data.repositories.auth.ISignUpRepo

class SignUpUseCase(
    private val repo: ISignUpRepo
) {
    operator fun invoke(req: SignUpReqDTO) = repo(req)
}