package org.ayaz.spx500.data.util.server

data class SPXServer(
    val host: String,
    val port: Int
) {
    companion object {
        private const val BASE = "ktor.deployment"
        const val HOST = "${BASE}.host"
        const val PORT = "${BASE}.port"

        const val DEFAULT_HOST = "0.0.0.0"
        const val DEFAULT_PORT = 8080
    }
}
