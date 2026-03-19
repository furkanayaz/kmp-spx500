package org.ayaz.finance.presentation.routes.crypto

import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.ayaz.finance.presentation.util.CallUtil.getPagingInfo

fun Route.cryptoRoutes() {
    authenticate {
        get(CryptoEndpoints.GET_DATA) {
            val (pageNo, pageSize) = call.getPagingInfo()
        }

        get(CryptoEndpoints.GET_DATA_DETAIL) {

        }
    }
}