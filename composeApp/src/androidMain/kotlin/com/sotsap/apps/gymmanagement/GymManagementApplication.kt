package com.sotsap.apps.gymmanagement

import com.sotsap.apps.gymmanagement.di.initKoin
import org.koin.core.KoinApplication

/**
 * The main application class for the Gym Management application.
 *
 * This class extends [AndroidApplication] and is responsible for initializing
 * the application-wide components, such as dependency injection using Koin.
 */
class GymManagementApplication: AndroidApplication() {

    /**
     * Provides the Koin application instance.
     *
     * This abstract function must be implemented by subclasses to provide the specific
     * configuration and modules for the Koin dependency injection framework.
     *
     * @return The configured [KoinApplication] instance.
     */
    override fun provideKoinApplication(): KoinApplication {
        return initKoin {
            standardKoinDependencies()
        }
    }

}