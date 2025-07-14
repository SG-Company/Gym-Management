package com.sotsap.apps.gymmanagement

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent

abstract class AndroidApplication: Application(), KoinComponent {

    /**
     * The Koin application instance.
     */
    private lateinit var koinApplication: KoinApplication

    /**
     * Provides a standard set of Koin dependencies for an Android application.
     *
     * This function is designed to be used within a Koin application setup block.
     * It includes basic configurations like:
     * - Setting up the Android logger for Koin.
     * - Providing the Android application context as a Koin dependency.
     *
     * Subclasses can call this function and then add their specific modules
     * to build their complete Koin application configuration.
     *
     * Example usage within `provideKoinApplication`:
     * ```kotlin
     * override fun provideKoinApplication(): KoinApplication {
     *     return koinApplication {
     *         standardKoinDependencies() // Include standard dependencies
     *         // Add your application-specific modules here
     *         modules(appModule, dataModule)
     *     }
     * }
     * ```
     *
     * @return A lambda function that configures a [KoinApplication] with standard Android dependencies.
     */
    fun koinDependencies(): KoinApplication.() -> Unit = {
        androidLogger()
        androidContext(this@AndroidApplication)
    }
}