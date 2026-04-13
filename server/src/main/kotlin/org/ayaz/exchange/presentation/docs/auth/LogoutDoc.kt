package org.ayaz.exchange.presentation.docs.auth

import io.ktor.http.HttpStatusCode
import io.ktor.openapi.jsonSchema
import io.ktor.server.routing.Route
import io.ktor.server.routing.openapi.describe
import io.ktor.utils.io.ExperimentalKtorApi
import org.ayaz.exchange.presentation.docs.DocTags.AUTHENTICATION_TAG

@OptIn(ExperimentalKtorApi::class)
fun Route.setLogoutDoc() {
    describe {
        tag(AUTHENTICATION_TAG)
        summary = "This endpoint is used for log out to expire your token..."
        description = "Logs the user out of their account."

        responses {
            HttpStatusCode.OK {
                jsonSchema<Boolean>()
                description = "User logged out of their account."
            }
        }
    }
}