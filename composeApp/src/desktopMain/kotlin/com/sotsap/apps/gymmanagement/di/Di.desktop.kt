package com.sotsap.apps.gymmanagement.di

import org.koin.core.KoinApplication

actual val platformDi: KoinApplication
    get() = KoinApplication.init()