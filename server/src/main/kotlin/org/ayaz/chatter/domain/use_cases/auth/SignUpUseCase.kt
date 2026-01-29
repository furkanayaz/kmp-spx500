package org.ayaz.bookstore.domain.use_cases.auth

import org.ayaz.bookstore.data.dto_s.auth.SignUpReqDTO
import org.ayaz.bookstore.data.repositories.auth.SignUpRepo
import org.koin.core.annotation.Single

@Single
class SignUpUseCase(
    private val signUpRepo: SignUpRepo
) {
    operator fun invoke(req: SignUpReqDTO) = signUpRepo.signUp(req)
}