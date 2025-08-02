package com.sotsap.apps.gymmanagement

import android.app.Activity
import android.os.Build
import androidx.compose.runtime.staticCompositionLocalOf

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

val LocalActivity = staticCompositionLocalOf<Activity> {
    error("LocalActivity not provided")
}