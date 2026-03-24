package org.ayaz.finance.presentation.routes.crypto

import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.ayaz.finance.domain.use_cases.crypto.CryptoDataDetailUseCase
import org.ayaz.finance.domain.use_cases.crypto.CryptoDataUseCase
import org.ayaz.finance.presentation.docs.crypto.setGetDataDetailDoc
import org.ayaz.finance.presentation.docs.crypto.setGetDataDoc
import org.ayaz.finance.presentation.util.CallUtil.sendErrorMessage
import org.ayaz.finance.presentation.util.CallUtil.getPagingInfo
import org.ayaz.finance.presentation.util.CallUtil.sendResponse
import org.koin.ktor.ext.inject

fun Route.cryptoRoutes() {
    authenticate {
        get(CryptoEndpoints.GET_DATA) {
            val (pageNo, pageSize) = call.getPagingInfo()
            val crpytoDataUseCase by inject<CryptoDataUseCase>()

            call.sendResponse(crpytoDataUseCase(pageSize, pageNo))
        }.setGetDataDoc()

        get(CryptoEndpoints.GET_DATA_DETAIL) {
            val id = call.request.queryParameters["id"]?.toIntOrNull()
            val convert = call.request.queryParameters["currency"]

            if (id == null) call.sendErrorMessage("fields.required", "id")
            if (convert == null) call.sendErrorMessage("fields.required", "currency")

            val crpytoDataDetailUseCase by inject<CryptoDataDetailUseCase>()
            call.sendResponse(crpytoDataDetailUseCase(id!!, convert!!))
        }.setGetDataDetailDoc()
    }
}