package org.ayaz.spx500.domain.use_cases.auth

import org.ayaz.spx500.data.dto_s.auth.LoginReqDTO
import org.ayaz.spx500.data.repositories.auth.ILoginRepo
import org.ayaz.spx500.data.util.jwt.JWTValues

class LoginUseCase(
    private val loginRepo: ILoginRepo
) {
    operator fun invoke(req: LoginReqDTO, jwtValues: JWTValues) = loginRepo(req, jwtValues)
}