package com.sotsap.apps.gymmanagement.features.initial.data.remote

import com.sotsap.apps.gymmanagement.core.models.Error
import com.sotsap.apps.gymmanagement.core.models.Result
import com.sotsap.apps.gymmanagement.core.network.supabase.supabaseClient
import com.sotsap.apps.gymmanagement.features.initial.domain.remote.InitialRepository
import io.github.jan.supabase.auth.auth

class InitialRepositoryImpl: InitialRepository {

    /**
     * Checks if a user session is currently active.
     *
     * @return True if a session is active, false otherwise.
     */
    override suspend fun isSessionActive(): Result<Boolean, Error> {
        supabaseClient.auth.awaitInitialization()
        val session = supabaseClient.auth.sessionManager.loadSession()
        if (session == null) return Result.Success(data = false)
        if (session.user == null) return Result.Success(data = false)
        return Result.Success(data = true)
    }

}