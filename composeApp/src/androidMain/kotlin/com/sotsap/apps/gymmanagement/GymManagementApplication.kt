package com.sotsap.apps.gymmanagement

import com.sotsap.apps.gymmanagement.di.initKoin
import org.koin.dsl.koinApplication

/**
 * The main application class for the Gym Management app.
 *
 * This class extends `AndroidApplication` and is responsible for initializing
 * application-wide components, such as Koin dependencies, upon creation.
 */
class GymManagementApplication: AndroidApplication() {

    override fun onCreate() {
        super.onCreate()
        dependencies()
    }

    /**
     * Initializes Koin dependencies for the application.
     *
     * This function sets up the Koin dependency injection framework by:
     * 1. Calling `koinDependencies()` which is an extension function (likely defined elsewhere)
     *    that declares the application's modules.
     * 2. Calling `initKoin()` which likely starts the Koin application context.
     *
     * @return A lambda function with `KoinApplication` as its receiver, configuring Koin.
     */
    private fun dependencies() = koinApplication {
        koinDependencies()
        initKoin()
    }

}