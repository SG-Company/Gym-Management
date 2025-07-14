package com.sotsap.apps.gymmanagement.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

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
}