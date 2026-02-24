package org.ayaz.spx500.domain.use_cases.auth

import org.ayaz.spx500.data.dto_s.auth.SignUpReqDTO
import org.ayaz.spx500.data.repositories.auth.ISignUpRepo

class SignUpUseCase(
    private val signUpRepo: ISignUpRepo
) {
    operator fun invoke(req: SignUpReqDTO) = signUpRepo.signUp(req)
}