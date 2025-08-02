package com.sotsap.apps.gymmanagement.features.login.data

import com.sotsap.apps.gymmanagement.core.models.Error
import com.sotsap.apps.gymmanagement.core.models.Result

/**
 * Repository interface for handling login operations.
 * This interface defines the contract for logging in a user.
 */
interface LoginRepository {

    enum class Errors: Error { AuthRest, Rest, Timeout, Request }

    /**
     * Logs in a user with the given email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return A [Result] indicating success ([Unit]) or an [Errors] object if login fails.
     */
    suspend fun login(email: String, password: String): Result<Unit, Errors>

}

