package org.ayaz.finance.presentation.routes.spx

import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.ayaz.finance.data.util.Response
import org.ayaz.finance.domain.use_cases.spx.GetSpxDataDetailUseCase
import org.ayaz.finance.domain.use_cases.spx.GetSpxDataUseCase
import org.ayaz.finance.presentation.util.CallUtil.getPagingInfo
import org.ayaz.finance.presentation.util.CallUtil.sendResponse
import org.koin.ktor.ext.inject

fun Route.spxRoutes() {
    authenticate {
        get(SpxEndpoints.GET_DATA) {
            val getSpxDataUseCase by inject<GetSpxDataUseCase>()
            val (pageNo, pageSize) = call.getPagingInfo()

            call.sendResponse(getSpxDataUseCase(pageNo, pageSize))
        }

        get(SpxEndpoints.GET_DATA_DETAIL) {
            val symbol = call.parameters["symbol"]
            if (symbol == null) call.sendResponse(Response.Error(errorMessages = listOf("spx.data.detail.symbol.required")))

            val getSpxDataDetailUseCase by inject<GetSpxDataDetailUseCase>()
            call.sendResponse(getSpxDataDetailUseCase(symbol!!))
        }
    }
}