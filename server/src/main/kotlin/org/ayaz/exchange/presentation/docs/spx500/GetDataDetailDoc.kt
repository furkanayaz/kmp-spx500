package org.ayaz.exchange.presentation.docs.spx500

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
        tag(DocTags.SPX500_TAG)
        summary = "This endpoint retrieves the detail of S&P 500 company..."
        description = "Retrieves the complete detail of S&P 500 company."

        parameters {
            path("symbol") {
                description = "Required to retrieve company detail. Examples: TSLA, AAPL, META etc..."
                required = true
            }
        }

        responses {
            HttpStatusCode.OK {
                description = "Retrieves the specific company detail according to the symbol name..."
                schema = jsonSchema<List<SpxDetailResDTO>>()
            }
        }
    }
}