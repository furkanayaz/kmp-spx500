package org.ayaz.exchange.domain.use_cases.auth

import org.ayaz.exchange.data.dto_s.auth.LoginReqDTO
import org.ayaz.exchange.data.repositories.auth.ILoginRepo
import org.ayaz.exchange.data.auth.jwt.JWTValues

class LoginUseCase(
    private val repo: ILoginRepo
) {
    operator fun invoke(req: LoginReqDTO, jwtValues: JWTValues) = repo(req, jwtValues)
}