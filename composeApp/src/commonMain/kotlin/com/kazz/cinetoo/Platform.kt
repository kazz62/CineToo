package com.kazz.cinetoo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform