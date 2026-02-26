package org.ayaz.spx500.presentation.routes.auth

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.ayaz.spx500.data.dto_s.auth.LoginReqDTO
import org.ayaz.spx500.data.dto_s.auth.LoginResDTO
import org.ayaz.spx500.data.dto_s.auth.SignUpReqDTO
import org.ayaz.spx500.data.util.Response
import org.ayaz.spx500.domain.use_cases.auth.LoginUseCase
import org.ayaz.spx500.domain.use_cases.auth.SignUpUseCase
import org.ayaz.spx500.domain.use_cases.auth.LogoutUseCase
import org.ayaz.spx500.presentation.util.CallUtil.getClaim
import org.ayaz.spx500.presentation.util.CallUtil.getJWTValues
import org.ayaz.spx500.presentation.util.CallUtil.require
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    post(AuthEndpoints.LOGIN) {
        val jwtValues = call.application.environment.config.getJWTValues()
        val reqModel = call.require<LoginReqDTO>()
        val loginUseCase: LoginUseCase by inject()

        when(val response = loginUseCase(reqModel, jwtValues)) {
            is Response.Error<LoginResDTO> -> call.respond(response.getHttpStatusCode(), response)
            is Response.Success<LoginResDTO> -> call.respond(HttpStatusCode.OK, response)
        }
    }

    post(AuthEndpoints.SIGN_UP) {
        val reqModel = call.require<SignUpReqDTO>()
        val signUpUseCase: SignUpUseCase by inject()

        when(val response = signUpUseCase(reqModel)) {
            is Response.Error<Boolean> -> call.respond(response.getHttpStatusCode(), response)
            is Response.Success<Boolean> -> call.respond(HttpStatusCode.OK, response)
        }
    }

    authenticate {
        get(AuthEndpoints.LOG_OUT) {
            val email = call.getClaim().email
            val logoutUseCase by inject<LogoutUseCase>()
            when(val response = logoutUseCase(email)) {
                is Response.Error<Boolean> -> call.respond(response.getHttpStatusCode(), response)
                is Response.Success<Boolean> -> call.respond(HttpStatusCode.OK, response)
            }
        }
    }
}