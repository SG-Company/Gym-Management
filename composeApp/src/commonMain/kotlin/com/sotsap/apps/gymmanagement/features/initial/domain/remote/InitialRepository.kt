package com.sotsap.apps.gymmanagement.features.initial.domain.remote

import com.sotsap.apps.gymmanagement.core.models.Error
import com.sotsap.apps.gymmanagement.core.models.Result

interface InitialRepository {

    /**
     * Checks if a user session is currently active.
     *
     * @return True if a session is active, false otherwise.
     */
    suspend fun isSessionActive(): Result<Boolean, Error>

}