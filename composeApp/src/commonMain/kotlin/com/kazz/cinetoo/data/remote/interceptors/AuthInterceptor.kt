package com.kazz.cinetoo.data.remote.interceptors

import io.ktor.client.plugins.api.*

val AuthInterceptor = createClientPlugin("AuthInterceptor", ::AuthInterceptorConfig) {
    val apiKey = pluginConfig.apiKey

    onRequest { request, _ ->
        request.headers.append("Authorization", "Bearer $apiKey")
    }
}

class AuthInterceptorConfig {
    var apiKey: String = ""
}
