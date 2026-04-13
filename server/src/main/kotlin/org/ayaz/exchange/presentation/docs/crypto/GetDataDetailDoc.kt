package org.ayaz.exchange.presentation.docs.crypto

import io.ktor.http.HttpStatusCode
import io.ktor.openapi.jsonSchema
import io.ktor.server.routing.Route
import io.ktor.server.routing.openapi.describe
import io.ktor.utils.io.ExperimentalKtorApi
import org.ayaz.exchange.data.dto_s.spx.SpxDetailResDTO
import org.ayaz.exchange.presentation.docs.DocTags

@OptIn(ExperimentalKtorApi::class)
fun Route.setGetDataDetailDoc() {
    describe {
        tag(DocTags.CRYPTO_TAG)
        summary = "This endpoint retrieves the detail of the crypto..."
        description = "Retrieves the complete detail info of the crypto."

        parameters {
            query("id") {
                description = "Required to retrieve crypto detail. Examples: 1 (Bitcoin), 2 (Litecoin), 3 (Namecoin) etc..."
                required = true
            }

            query("currency") {
                description = "Required to retrieve the latest quote price data (1H, 24H, 7D, etc.) default is USD."
                required = false
            }
        }

        responses {
            HttpStatusCode.OK {
                description = "Retrieves the specific crypto detail according to the id..."
                schema = jsonSchema<List<SpxDetailResDTO>>()
            }
        }
    }
}