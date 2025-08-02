package com.sotsap.apps.gymmanagement.core.system

import androidx.compose.runtime.Composable

/**
 * Closes the application.
 *
 * This function is used to gracefully exit the application. It may perform cleanup tasks
 * before terminating the process. The exact behavior can vary depending on the platform
 * implementation.
 */
expect fun closeApp()