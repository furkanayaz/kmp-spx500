package org.ayaz.spx500.presentation.routes.auth

import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.ayaz.spx500.data.dto_s.auth.LoginReqDTO
import org.ayaz.spx500.data.dto_s.auth.SignUpReqDTO
import org.ayaz.spx500.domain.use_cases.auth.LoginUseCase
import org.ayaz.spx500.domain.use_cases.auth.SignUpUseCase
import org.ayaz.spx500.domain.use_cases.auth.LogoutUseCase
import org.ayaz.spx500.presentation.util.CallUtil.getClaim
import org.ayaz.spx500.presentation.util.CallUtil.getJWTValues
import org.ayaz.spx500.presentation.util.CallUtil.require
import org.ayaz.spx500.presentation.util.CallUtil.sendResponse
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    post(AuthEndpoints.LOGIN) {
        val jwtValues = call.application.environment.config.getJWTValues()
        val reqModel = call.require<LoginReqDTO>()
        val loginUseCase: LoginUseCase by inject()

        val response = loginUseCase(reqModel, jwtValues)
        call.sendResponse(response)
    }

    post(AuthEndpoints.SIGN_UP) {
        val reqModel = call.require<SignUpReqDTO>()
        val signUpUseCase: SignUpUseCase by inject()

        val response = signUpUseCase(reqModel)
        call.sendResponse(response)
    }

    authenticate {
        get(AuthEndpoints.LOG_OUT) {
            val email = call.getClaim().email
            val logoutUseCase by inject<LogoutUseCase>()

            val response = logoutUseCase(email)
            call.sendResponse(response)
        }
    }
}