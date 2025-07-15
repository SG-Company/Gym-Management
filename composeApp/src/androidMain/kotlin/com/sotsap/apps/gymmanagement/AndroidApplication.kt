package com.sotsap.apps.gymmanagement

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent

/**
 * Base class for Android applications that integrate with Koin for dependency injection.
 *
 * This abstract class provides a foundational setup for using Koin within an Android application.
 * It ensures that the Koin application instance is properly initialized and provides a convenient
 * way to define standard Koin dependencies common to most Android applications.
 *
 * Subclasses should extend this class and implement the `provideKoinApplication` method
 * (if not already provided by `KoinApplication` or a similar interface they might implement
 * indirectly) to define their specific Koin modules.
 *
 * The [koinDependencies] function offers a reusable block for setting up essential Android-related
 * Koin configurations, such as logging and providing the application context.
 */
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
    fun standardKoinDependencies(): KoinApplication.() -> Unit = {
        androidLogger()
        androidContext(this@AndroidApplication)
    }

    /**
     * Called when the application is starting, before any activity, service, or receiver objects
     * (excluding content providers) have been created.
     *
     * Initializes Koin dependency injection framework for the application.
     */
    override fun onCreate() {
        super.onCreate()
        koinApplication = provideKoinApplication()
        onCreateImpl()
    }

    /**
     * Provides the Koin application instance.
     *
     * This abstract function must be implemented by subclasses to provide the specific
     * configuration and modules for the Koin dependency injection framework.
     *
     * @return The configured [KoinApplication] instance.
     */
    abstract fun provideKoinApplication(): KoinApplication

    /**
     * Optional hook for subclasses to perform additional initialization after the application has been created
     * and Koin has been initialized.
     *
     * This method is called at the end of the `onCreate` lifecycle method.
     */
    open fun onCreateImpl() { /* Intentionally left blank */ }


}