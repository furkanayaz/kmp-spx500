package org.ayaz.spx500.presentation.routes.spx

import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.spxRoutes() {
    authenticate {
        get(SpxEndpoints.GET_SPX_DATA) {

        }

        get(SpxEndpoints.GET_SPX_DATA_DETAIL) {

        }
    }
}