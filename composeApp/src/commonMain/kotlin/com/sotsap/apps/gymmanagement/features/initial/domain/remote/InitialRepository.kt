package com.sotsap.apps.gymmanagement.features.initial.domain.remote

import com.sotsap.apps.gymmanagement.core.models.Error
import com.sotsap.apps.gymmanagement.core.models.Response
import com.sotsap.apps.gymmanagement.core.models.Result
import com.sotsap.apps.gymmanagement.core.network.supabase.models.SupabaseErrors

/**
 * Repository interface for handling initial application logic, such as session management.
 */
interface InitialRepository {

    /**
     * Checks if a user session is currently active.
     *
     * @return True if a session is active, false otherwise.
     */
    suspend fun isSessionActive(): Response<Boolean, SupabaseErrors>

}