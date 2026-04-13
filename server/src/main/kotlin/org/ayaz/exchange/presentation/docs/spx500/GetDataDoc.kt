package org.ayaz.exchange.presentation.docs.spx500

import io.ktor.http.HttpStatusCode
import io.ktor.openapi.jsonSchema
import io.ktor.server.routing.Route
import io.ktor.server.routing.openapi.describe
import io.ktor.utils.io.ExperimentalKtorApi
import org.ayaz.exchange.data.dto_s.spx.SpxResDTO
import org.ayaz.exchange.presentation.docs.DocTags

@OptIn(ExperimentalKtorApi::class)
fun Route.setGetDataDoc() {
    describe {
        tag(DocTags.SPX500_TAG)
        summary = "This endpoint retrieves the list of all S&P 500 companies..."
        description = "Retrieves the complete list of S&P 500 companies, including basic information such as symbol, name, and sector."

        parameters {
            query("pageNo") {
                description = "Required to retrieve company list."
                required = true
            }

            query("pageSize") {
                description = "Not required but default is 10 company list per page."
                required = false
            }
        }

        responses {
            HttpStatusCode.OK {
                description = "Indicates that the company list has been successfully retrieved..."
                schema = jsonSchema<List<SpxResDTO>>()
            }
        }
    }
}