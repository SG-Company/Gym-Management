package com.sotsap.apps.gymmanagement

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform