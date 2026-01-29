package org.ayaz.bookstore.domain.use_cases.auth

import org.ayaz.bookstore.data.dto_s.auth.LoginReqDTO
import org.ayaz.bookstore.data.repositories.auth.LoginRepo
import org.koin.core.annotation.Single

@Single
class LoginUseCase(
    private val loginRepo: LoginRepo
) {
    suspend operator fun invoke(req: LoginReqDTO) = loginRepo.login(req)
}