package org.ayaz.exchange.data.di

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.ayaz.exchange.data.util.CoinMarketCap
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class NetworkModule {

    @Single
    @CoinMarketCap
    fun provideHTTPClient(): HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                encodeDefaults = true
                explicitNulls = true
            })
        }

        defaultRequest {
            url("https://pro-api.coinmarketcap.com")
            headers {
                append("X-CMC_PRO_API_KEY", "6f5321fd-2927-4386-87b2-a1da537b4b8f")
                accept(ContentType.Application.Json)
            }
        }
    }

}