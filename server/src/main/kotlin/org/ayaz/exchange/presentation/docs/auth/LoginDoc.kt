package org.ayaz.exchange.presentation.docs.auth

import io.ktor.http.HttpStatusCode
import io.ktor.openapi.ExampleObject
import io.ktor.openapi.GenericElement
import io.ktor.openapi.jsonSchema
import io.ktor.server.routing.Route
import io.ktor.server.routing.openapi.describe
import io.ktor.utils.io.ExperimentalKtorApi
import org.ayaz.exchange.data.dto_s.auth.LoginReqDTO
import org.ayaz.exchange.data.dto_s.auth.LoginResDTO
import org.ayaz.exchange.presentation.docs.DocTags.AUTHENTICATION_TAG

@OptIn(ExperimentalKtorApi::class)
fun Route.setLogicDoc() {
    describe {
        tag(AUTHENTICATION_TAG)
        summary = "This endpoint is used for user login to kmp-exchange..."
        description = "You can log in to kmp-exchange to get a token by using this endpoint."

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
                description = "User can receive a token from kmp-exchange..."
                schema = jsonSchema<LoginResDTO>()
            }

            HttpStatusCode.BadRequest {
                description = "The user cannot obtain a token from kmp-exchange due to an invalid email address or password."
                schema = jsonSchema<LoginResDTO>()
            }
        }
    }
}