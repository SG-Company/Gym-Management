package com.sotsap.apps.gymmanagement.core.system

import android.app.Activity
import androidx.compose.runtime.Composable
import com.sotsap.apps.gymmanagement.LocalActivity
import kotlin.system.exitProcess

actual fun closeApp() { exitProcess(0) }