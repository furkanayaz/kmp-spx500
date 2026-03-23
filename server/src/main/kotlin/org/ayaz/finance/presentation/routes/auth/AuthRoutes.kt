package org.ayaz.finance.presentation.routes.auth

import io.ktor.http.HttpStatusCode
import io.ktor.openapi.ExampleObject
import io.ktor.openapi.GenericElement
import io.ktor.openapi.jsonSchema
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.openapi.describe
import io.ktor.server.routing.post
import io.ktor.utils.io.ExperimentalKtorApi
import org.ayaz.finance.data.dto_s.auth.LoginReqDTO
import org.ayaz.finance.data.dto_s.auth.LoginResDTO
import org.ayaz.finance.data.dto_s.auth.SignUpReqDTO
import org.ayaz.finance.domain.use_cases.auth.LoginUseCase
import org.ayaz.finance.domain.use_cases.auth.SignUpUseCase
import org.ayaz.finance.domain.use_cases.auth.LogoutUseCase
import org.ayaz.finance.presentation.util.CallUtil.getClaim
import org.ayaz.finance.presentation.util.CallUtil.getJWTValues
import org.ayaz.finance.presentation.util.CallUtil.require
import org.ayaz.finance.presentation.util.CallUtil.sendErrorMessage
import org.ayaz.finance.presentation.util.CallUtil.sendResponse
import org.koin.ktor.ext.inject

private const val SWAGGER_TAG = "Authentication"

@OptIn(ExperimentalKtorApi::class)
fun Route.authRoutes() {
    post(AuthEndpoints.LOGIN) {
        val jwtValues = call.application.environment.config.getJWTValues()
        val reqModel = call.require<LoginReqDTO>()
        val loginUseCase: LoginUseCase by inject()

        val response = loginUseCase(reqModel, jwtValues)
        call.sendResponse(response)
    }.describe {
        tag(SWAGGER_TAG)
        summary = "User log in to kmp-finance..."
        description = "You can log in to kmp-finance to get a token by using this endpoint."

        requestBody {
            required = true
            description = "Login request body must contain a valid email address and password to log in successfully."

            content {
                schema = jsonSchema<LoginReqDTO>()

                example(
                    "login_success",
                    ExampleObject(
                        "Success Example",
                        "As you can see, the email address matches a valid email regex pattern. The password must be 8–20 characters long and contain at least one lowercase letter, one uppercase letter, one special character, and one number.",
                        GenericElement(LoginReqDTO("furkanayaz.dev@gmail.com", "FurkanAyaz321$"))
                    )
                )
                example(
                    "login_failure",
                    ExampleObject(
                        "Failure Example",
                        "As you can see, the email address does not match a valid email regex pattern because it is missing the ‘@’ character. The password is not valid because it does not contain any uppercase letters and one special character.",
                        GenericElement(LoginReqDTO("furkanayaz.devgmail.com", "furkan123"))
                    )
                )
            }
        }

        responses {
            HttpStatusCode.OK {
                description = "User can receive a token from kmp-finance..."
                schema = jsonSchema<LoginResDTO>()
            }

            HttpStatusCode.BadRequest {
                description = "The user cannot obtain a token from kmp-finance due to an invalid email address or password."
                schema = jsonSchema<LoginResDTO>()
            }
        }
    }

    post(AuthEndpoints.SIGN_UP) {
        val reqModel = call.require<SignUpReqDTO>()
        val signUpUseCase: SignUpUseCase by inject()

        val response = signUpUseCase(reqModel)
        call.sendResponse(response)
    }.describe {
        tag(SWAGGER_TAG)
        summary = "User sign up to kmp-finance..."
        description = "You can sign up to kmp-finance to get an account by using this endpoint."

        requestBody {
            required = true
            description = "Sign-up request body must include a name, lastName, a valid email address, and a password to complete registration."

            content {
                schema = jsonSchema<SignUpReqDTO>()

                example(
                    "sign_up_success",
                    ExampleObject(
                        "Success Example",
                        "As you can see, name and lastName must be min 2 characters long and the email address matches a valid email regex pattern. The password must be 8–20 characters long and contain at least one lowercase letter, one uppercase letter, one special character, and one number.",
                        GenericElement(SignUpReqDTO("Fa", "Az", "furkanayaz.dev@gmail.com", "FurkanAyaz321$"))
                    )
                )
                example(
                    "sign_up_failure",
                    ExampleObject(
                        "Failure Example",
                        "As you can see, name and lastName not match 2 characters long and the email address does not match a valid email regex pattern because it is missing the ‘@’ character. The password is not valid because it does not contain any uppercase letters and one special character.",
                        GenericElement(SignUpReqDTO("F", "A", "furkanayaz.devgmail.com", "furkan123"))
                    )
                )
            }
        }

        responses {
            HttpStatusCode.OK {
                schema = jsonSchema<Boolean>()
                description = "User creates an account successfully..."
            }
        }
    }

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
        }.describe {
            tag(SWAGGER_TAG)
            summary = "Log out to expire your token..."
            description = "Logs the user out of their account."

            responses {
                HttpStatusCode.OK {
                    jsonSchema<Boolean>()
                    description = "User logged out of their account."
                }
            }
        }
    }
}