package com.sotsap.apps.gymmanagement.di

import com.sotsap.apps.gymmanagement.features.initial.domain.remote.InitialRepository
import com.sotsap.apps.gymmanagement.features.initial.data.remote.InitialRepositoryImpl
import com.sotsap.apps.gymmanagement.features.initial.presentation.InitialViewModel
import com.sotsap.apps.gymmanagement.features.login.data.LoginRepositoryImpl
import com.sotsap.apps.gymmanagement.features.login.domain.LoginRepository
import com.sotsap.apps.gymmanagement.features.login.presentation.LoginViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
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
 * Initializes Koin dependency injection.
 *
 * This function starts Koin and loads the platform-specific DI modules.
 */
fun initKoin() = startKoin {
    platformDi
    gymModules
}

/**
 * Loads the initial Koin modules for the application.
 *
 * This function is an extension on `KoinApplication` and is responsible for
 * registering the core modules required for the gym management application.
 *
 * It currently loads the `initialModule`.
 */
private val KoinApplication.gymModules: KoinApplication get() = modules(
    initialModule,
    loginModule
)

/**
 * Defines the initial Koin module for the application.
 *
 * This module provides the initial dependencies required for the application's core functionality.
 * It includes:
 *  - `InitialRepository`: A singleton instance of `InitialRepositoryImpl` responsible for data access.
 *  - `InitialViewModel`: A factory for creating `InitialViewModel` instances, which depend on `InitialRepository`.
 */
private val initialModule = module {
    single<InitialRepository> { InitialRepositoryImpl() }
    factory { InitialViewModel(get()) }
}

/**
 * Defines the login Koin module for the application.
 *
 * This module provides the dependencies required for the login feature.
 * It includes:
 *  - `LoginRepository`: A singleton instance of `LoginRepositoryImpl` responsible for login-related data access.
 *  - `LoginViewModel`: A factory for creating `LoginViewModel` instances, which depend on `LoginRepository`.
 */
private val loginModule = module {
    single<LoginRepository> { LoginRepositoryImpl() }
    factory { LoginViewModel(get() ) }
}