package com.sotsap.apps.gymmanagement.features.login.domain

import com.sotsap.apps.gymmanagement.core.models.Error
import com.sotsap.apps.gymmanagement.core.models.Result

/**
 * Repository interface for handling login operations.
 * This interface defines the contract for logging in users and managing potential errors.
 */
interface LoginRepository {

    /**
     * Represents the possible errors that can occur during the login process.
     */
    enum class Errors: Error {
        AuthRest,
        Timeout,
        Request
    }

    /**
     * Logs in a user with the given email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return A [Result] indicating success or failure.
     *         If successful, the [Result] will contain [Unit].
     *         If unsuccessful, the [Result] will contain an [Errors] object.
     */
    suspend fun login(email: String, password: String): Result<Unit, Errors>

}