package com.kazz.cinetoo.data.remote.api

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import com.kazz.cinetoo.data.remote.interceptors.AuthInterceptor

expect fun createHttpClient(apiKey: String): HttpClient

fun createBaseHttpClient(apiKey: String, engine: HttpClient): HttpClient {
    return engine.config {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }

        install(AuthInterceptor) {
            this.apiKey = apiKey
        }
    }
}
