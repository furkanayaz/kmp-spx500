package org.ayaz.exchange.presentation.docs.crypto

import io.ktor.http.HttpStatusCode
import io.ktor.openapi.jsonSchema
import io.ktor.server.routing.Route
import io.ktor.server.routing.openapi.describe
import io.ktor.utils.io.ExperimentalKtorApi
import org.ayaz.exchange.data.dto_s.crypto.CryptoMapResDTO
import org.ayaz.exchange.presentation.docs.DocTags

@OptIn(ExperimentalKtorApi::class)
fun Route.setGetDataDoc() {
    describe {
        tag(DocTags.CRYPTO_TAG)
        summary = "This endpoint retrieves the list of all cryptos..."
        description = "Retrieves the complete list of cryptos, including basic information such as symbol, name, and slug."

        parameters {
            query("pageNo") {
                description = "Required to retrieve crypto list."
                required = true
            }

            query("pageSize") {
                description = "Not required but default is 10 crypto list per page."
                required = true
            }
        }

        responses {
            HttpStatusCode.OK {
                description = "Indicates that the crypto list has been successfully retrieved..."
                schema = jsonSchema<List<CryptoMapResDTO>>()
            }
        }
    }
}