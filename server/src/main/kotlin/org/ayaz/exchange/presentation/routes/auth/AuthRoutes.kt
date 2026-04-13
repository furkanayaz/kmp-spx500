package org.ayaz.exchange.presentation.routes.auth

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.ayaz.exchange.data.dto_s.auth.LoginReqDTO
import org.ayaz.exchange.data.dto_s.auth.SignUpReqDTO
import org.ayaz.exchange.domain.use_cases.auth.LoginUseCase
import org.ayaz.exchange.domain.use_cases.auth.SignUpUseCase
import org.ayaz.exchange.domain.use_cases.auth.LogoutUseCase
import org.ayaz.exchange.presentation.docs.auth.setLogicDoc
import org.ayaz.exchange.presentation.docs.auth.setLogoutDoc
import org.ayaz.exchange.presentation.docs.auth.setSignUpDoc
import org.ayaz.exchange.presentation.util.CallUtil.getClaim
import org.ayaz.exchange.presentation.util.CallUtil.getJWTValues
import org.ayaz.exchange.presentation.util.CallUtil.require
import org.ayaz.exchange.presentation.util.CallUtil.sendErrorMessage
import org.ayaz.exchange.presentation.util.CallUtil.sendResponse
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    post(AuthEndpoints.LOGIN) {
        val jwtValues = call.application.environment.config.getJWTValues()
        val reqModel = call.require<LoginReqDTO>()
        val loginUseCase: LoginUseCase by inject()

        val response = loginUseCase(reqModel, jwtValues)
        call.sendResponse(response)
    }.setLogicDoc()

    post(AuthEndpoints.SIGN_UP) {
        val reqModel = call.require<SignUpReqDTO>()
        val signUpUseCase: SignUpUseCase by inject()

        val response = signUpUseCase(reqModel)
        call.sendResponse(response)
    }.setSignUpDoc()

    authenticate {
        get(AuthEndpoints.LOG_OUT) {
            val email = call.getClaim().email

            if (email.isNullOrEmpty()) call.sendErrorMessage(
                "server.unknown.error",
                code = HttpStatusCode.InternalServerError
            )

            val logoutUseCase by inject<LogoutUseCase>()

            val response = logoutUseCase(email!!)
            call.sendResponse(response)
        }.setLogoutDoc()
    }
}