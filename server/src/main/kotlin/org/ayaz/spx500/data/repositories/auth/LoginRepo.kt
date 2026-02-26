package org.ayaz.spx500.data.repositories.auth

import org.ayaz.spx500.data.dto_s.auth.LoginReqDTO
import org.ayaz.spx500.data.dto_s.auth.LoginResDTO
import org.ayaz.spx500.data.sessions.token.TokenSession
import org.ayaz.spx500.data.uow_s.auth.ILoginUow
import org.ayaz.spx500.data.util.Response
import org.ayaz.spx500.data.util.jwt.JWTUtil
import org.ayaz.spx500.data.util.jwt.JWTValues
import org.ayaz.spx500.domain.mapper.login.LoginResMapper
import org.ayaz.spx500.domain.models.user.UserModel
import org.ayaz.spx500.domain.util.Resource

fun interface ILoginRepo {
    operator fun invoke(req: LoginReqDTO, jwtValues: JWTValues): Response<LoginResDTO>
}

class LoginRepo(
    private val loginUow: ILoginUow,
    private val jwtUtil: JWTUtil,
    private val tokenSession: TokenSession,
    private val loginResMapper: LoginResMapper
): ILoginRepo {
    override fun invoke(req: LoginReqDTO, jwtValues: JWTValues): Response<LoginResDTO> {
        return when(val response = loginUow(req)) {
            is Resource.Error<UserModel> -> Response.Error(errorMessages = response.messages)
            is Resource.Success<UserModel> -> {
                val token = jwtUtil.createToken(jwtValues, req.email, req.password)

                if (tokenSession.addToken(response.item.uuid, token)) {
                    val responseItem = loginResMapper.toModel(response.item).copy(token = token)
                    Response.Success(item = responseItem)
                } else {
                    Response.Error(code = 500, errorMessages = listOf("unknown.error"))
                }
            }
        }
    }
}