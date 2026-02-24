package org.ayaz.spx500.presentation.routes.auth

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.ayaz.spx500.data.dto_s.auth.LoginReqDTO
import org.ayaz.spx500.data.dto_s.auth.SignUpReqDTO
import org.ayaz.spx500.data.sessions.token.TokenSession
import org.ayaz.spx500.data.util.Response
import org.ayaz.spx500.data.util.jwt.JWTUtil
import org.ayaz.spx500.domain.mapper.login.LoginResMapper
import org.ayaz.spx500.domain.models.user.UserModel
import org.ayaz.spx500.domain.use_cases.auth.LoginUseCase
import org.ayaz.spx500.domain.use_cases.auth.SignUpUseCase
import org.ayaz.spx500.domain.util.Resource
import org.ayaz.spx500.presentation.util.CallUtil.getJWTValues
import org.ayaz.spx500.presentation.util.CallUtil.require
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    post(AuthEndpoints.LOGIN) {
        val reqModel = call.require<LoginReqDTO>()
        val loginUseCase: LoginUseCase by inject()
        val jwtUtil: JWTUtil by inject()
        val tokenSession: TokenSession by inject()
        val loginResMapper: LoginResMapper by inject()

        when(val response = loginUseCase(reqModel)) {
            is Resource.Error<UserModel> -> call.respond(HttpStatusCode.BadRequest, Response.Error(errorMessages = response.messages))
            is Resource.Success<UserModel> -> {
                val jwtValues = call.application.environment.config.getJWTValues()
                val token = jwtUtil.createToken(jwtValues, reqModel.email, reqModel.password)

                if (tokenSession.addToken(response.item.uuid, token).isNotEmpty()) {
                    val responseItem = loginResMapper.toModel(response.item).copy(token = token)
                    call.respond(HttpStatusCode.OK, Response.Success(item = responseItem))
                } else {
                    call.respond(HttpStatusCode.InternalServerError, Response.Error(errorMessages = emptyList()))
                }
            }
        }
    }

    post(AuthEndpoints.SIGN_UP) {
        val reqModel = call.require<SignUpReqDTO>()
        val signUpUseCase: SignUpUseCase by inject()

        when(val response = signUpUseCase(reqModel)) {
            is Resource.Error<Boolean> -> call.respond(HttpStatusCode.BadRequest, Response.Error(errorMessages = response.messages))
            is Resource.Success<Boolean> -> call.respond(HttpStatusCode.OK, Response.Success(item = null, informationMessage = "Your account is created."))
        }
    }

    authenticate {
        get(AuthEndpoints.LOG_OUT) {

        }
    }
}