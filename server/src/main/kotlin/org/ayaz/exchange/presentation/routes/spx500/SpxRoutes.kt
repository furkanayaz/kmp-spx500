package org.ayaz.exchange.presentation.routes.spx500

import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.ayaz.exchange.domain.use_cases.spx.GetSpxDataDetailUseCase
import org.ayaz.exchange.domain.use_cases.spx.GetSpxDataUseCase
import org.ayaz.exchange.presentation.docs.spx500.setGetDataDetailDoc
import org.ayaz.exchange.presentation.docs.spx500.setGetDataDoc
import org.ayaz.exchange.presentation.util.CallUtil.getPagingInfo
import org.ayaz.exchange.presentation.util.CallUtil.sendErrorMessage
import org.ayaz.exchange.presentation.util.CallUtil.sendResponse
import org.koin.ktor.ext.inject

fun Route.spxRoutes() {
    authenticate {
        get(SpxEndpoints.GET_DATA) {
            val getSpxDataUseCase by inject<GetSpxDataUseCase>()
            val (pageNo, pageSize) = call.getPagingInfo()

            call.sendResponse(getSpxDataUseCase(pageNo, pageSize))
        }.setGetDataDoc()

        get(SpxEndpoints.GET_DATA_DETAIL) {
            val symbol = call.parameters["symbol"]
            if (symbol == null) call.sendErrorMessage("fields.required", "symbol")

            val getSpxDataDetailUseCase by inject<GetSpxDataDetailUseCase>()
            call.sendResponse(getSpxDataDetailUseCase(symbol!!))
        }.setGetDataDetailDoc()
    }
}