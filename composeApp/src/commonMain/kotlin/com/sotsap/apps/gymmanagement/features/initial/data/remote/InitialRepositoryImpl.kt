package com.sotsap.apps.gymmanagement.features.initial.data.remote

import com.sotsap.apps.gymmanagement.core.models.Response
import com.sotsap.apps.gymmanagement.core.network.supabase.models.SupabaseErrors
import com.sotsap.apps.gymmanagement.core.network.supabase.supabaseClient
import com.sotsap.apps.gymmanagement.features.initial.domain.remote.InitialRepository
import io.github.jan.supabase.auth.auth

/**
 * Implementation of [InitialRepository] that interacts with Supabase for session management.
 */
class InitialRepositoryImpl: InitialRepository {

    /**
     * Checks if a user session is currently active.
     *
     * @return True if a session is active, false otherwise.
     */
    override suspend fun isSessionActive(): Response<Boolean, SupabaseErrors> {
        supabaseClient.auth.awaitInitialization()
        val session = supabaseClient.auth.sessionManager.loadSession()
        if (session == null) return Response.Success(data = false)
        if (session.user == null) return Response.Success(data = false)
        return Response.Success(data = true)
    }

}