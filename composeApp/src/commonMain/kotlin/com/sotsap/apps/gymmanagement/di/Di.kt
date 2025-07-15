package com.sotsap.apps.gymmanagement.di

import com.sotsap.apps.gymmanagement.core.di.BaseDi
import com.sotsap.apps.gymmanagement.features.login.presentation.LoginViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

/**
 * Represents the platform-specific Koin application instance.
 *
 * This property provides access to the Koin dependency injection container,
 * which is configured differently for each platform (e.g., Android, iOS).
 * It allows platform-specific modules and dependencies to be registered and resolved.
 */
expect val platformDi: KoinApplication

/**
 * Initializes Koin for the application.
 *
 * This function sets up the Koin dependency injection framework by:
 * 1. Executing any provided platform-specific Koin configuration (`app`).
 * 2. Loading the common modules, including `loginDi`.
 *
 * @param app A lambda function that allows for platform-specific Koin configuration.
 *            This is typically used to register platform-specific dependencies or modules.
 *            It defaults to an empty lambda if no platform-specific configuration is needed.
 * @return The initialized KoinApplication instance.
 */
fun initKoin(app: KoinApplication.() -> Unit = {}) = BaseDi.setup {
    app()
    modules(
        listOf(
            loginDi
        )
    )
}

/**
 * Defines the Koin module for login-related dependencies.
 *
 * This module provides the `LoginViewModel` as a factory, meaning a new instance
 * will be created each time it's requested.
 */
private val loginDi: Module
    get() = module {
        factory { LoginViewModel() }
    }