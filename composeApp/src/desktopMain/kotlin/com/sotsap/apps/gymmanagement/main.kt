package com.sotsap.apps.gymmanagement

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.sotsap.apps.gymmanagement.di.initKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "GymManagement",
    ) {
        initKoin()
        App()
    }
}