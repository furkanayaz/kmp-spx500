package org.ayaz.spx500.domain.use_cases.auth

import org.ayaz.spx500.data.dto_s.auth.LoginReqDTO
import org.ayaz.spx500.data.repositories.auth.ILoginRepo

class LoginUseCase(
    private val loginRepo: ILoginRepo
) {
    suspend operator fun invoke(req: LoginReqDTO) = loginRepo.login(req)
}