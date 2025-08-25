package com.sotsap.apps.gymmanagement.features.login.domain

import com.sotsap.apps.gymmanagement.core.models.Response
import com.sotsap.apps.gymmanagement.core.models.Result
import com.sotsap.apps.gymmanagement.core.network.supabase.models.SupabaseErrors

/**
 * Repository interface for handling login operations.
 * This interface defines the contract for logging in users and managing potential errors.
 */
interface LoginRepository {

    /**
     * Logs in a user with the given email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return A [Result] indicating success or failure.
     *         If successful, the [Result] will contain [Unit].
     *         If unsuccessful, the [Result] will contain an [Errors] object.
     */
    suspend fun login(email: String, password: String): Response<Unit, SupabaseErrors>

    /**
     * Retrieves the profile information of the currently logged-in user.
     *
     * @return A [Result] containing the [ProfileModel] if successful,
     *         or an [Errors] object if an error occurred.
     */
    suspend fun getProfileInfo(): Response<ProfileModel, SupabaseErrors>

}