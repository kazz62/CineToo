package com.kazz.cinetoo.data.remote.api

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*

actual fun createHttpClient(apiKey: String): HttpClient {
    return HttpClient(OkHttp) {
        engine {
            config {
                followRedirects(true)
            }
        }
    }.let { createBaseHttpClient(apiKey, it) }
}
