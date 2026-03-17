package org.ayaz.finance.domain.use_cases.auth

import org.ayaz.finance.data.dto_s.auth.LoginReqDTO
import org.ayaz.finance.data.repositories.auth.ILoginRepo
import org.ayaz.finance.data.util.jwt.JWTValues

class LoginUseCase(
    private val loginRepo: ILoginRepo
) {
    operator fun invoke(req: LoginReqDTO, jwtValues: JWTValues) = loginRepo(req, jwtValues)
}