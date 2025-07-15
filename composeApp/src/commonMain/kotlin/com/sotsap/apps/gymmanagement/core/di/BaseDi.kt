package com.sotsap.apps.gymmanagement.core.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * BaseDi is a singleton object that provides a centralized mechanism for setting up
 * the Koin dependency injection framework. It offers a flexible way to initialize Koin,
 * allowing for both predefined configurations and additional custom Koin application setup.
 *
 * This object is designed to be the entry point for Koin initialization within the application.
 */
object BaseDi {

    /**
     * Initializes the core Koin dependency injection framework.
     *
     * This function is a flexible way to start Koin, allowing for both a predefined
     * configuration and additional, custom Koin application setup.
     *
     * @param configuration An optional KoinAppDeclaration lambda that can be used to
     *                      apply a pre-defined set of Koin configurations (e.g., modules, properties).
     *                      This is invoked first if provided.
     * @param koinApplication A lambda function that allows for further customization of the
     *                        KoinApplication instance after the optional `configuration` is applied.
     *                        This is invoked after `configuration` (if provided) or directly if
     *                        `configuration` is null.
     * @return The started KoinApplication instance.
     */
    fun setup(
        configuration: KoinAppDeclaration? = null,
        koinApplication: KoinApplication.() -> Unit = {},
    ) = startKoin {
        configuration?.invoke(this)
        koinApplication.invoke(this)
    }

}