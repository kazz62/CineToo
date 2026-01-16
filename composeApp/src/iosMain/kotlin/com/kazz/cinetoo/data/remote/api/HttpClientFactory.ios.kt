package com.kazz.cinetoo.data.remote.api

import io.ktor.client.*
import io.ktor.client.engine.darwin.*

actual fun createHttpClient(apiKey: String): HttpClient {
    return HttpClient(Darwin) {
        engine {
            configureRequest {
                setAllowsCellularAccess(true)
            }
        }
    }.let { createBaseHttpClient(apiKey, it) }
}
