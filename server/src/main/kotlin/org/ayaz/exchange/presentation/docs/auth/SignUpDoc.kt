package org.ayaz.exchange.presentation.docs.auth

import io.ktor.http.HttpStatusCode
import io.ktor.openapi.ExampleObject
import io.ktor.openapi.GenericElement
import io.ktor.openapi.jsonSchema
import io.ktor.server.routing.Route
import io.ktor.server.routing.openapi.describe
import io.ktor.utils.io.ExperimentalKtorApi
import org.ayaz.exchange.data.dto_s.auth.SignUpReqDTO
import org.ayaz.exchange.presentation.docs.DocTags.AUTHENTICATION_TAG

@OptIn(ExperimentalKtorApi::class)
fun Route.setSignUpDoc() {
    describe {
        tag(AUTHENTICATION_TAG)
        summary = "This endpoint is used for user sign up to kmp-exchange..."
        description = "You can sign up to kmp-exchange to get an account by using this endpoint."

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
}