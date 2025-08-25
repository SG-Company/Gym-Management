package com.sotsap.apps.gymmanagement.features.login.data

import com.sotsap.apps.gymmanagement.core.models.Response
import com.sotsap.apps.gymmanagement.core.models.Result
import com.sotsap.apps.gymmanagement.core.network.supabase.methods.supabaseRequest
import com.sotsap.apps.gymmanagement.core.network.supabase.models.SupabaseErrors
import com.sotsap.apps.gymmanagement.core.network.supabase.supabaseClient
import com.sotsap.apps.gymmanagement.core.utilities.Logger
import com.sotsap.apps.gymmanagement.features.login.domain.LoginRepository
import com.sotsap.apps.gymmanagement.features.login.domain.ProfileModel
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from

/**
 * Implementation of the [LoginRepository] interface.
 * This class handles the actual login and profile information retrieval logic
 * by interacting with the Supabase backend.
 */
class LoginRepositoryImpl: LoginRepository {

    /**
     * Logs in a user with the given email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return A [Result] indicating success or failure.
     *         If successful, the [Result] will contain [Unit].
     *         If unsuccessful, the [Result] will contain an [Errors] object.
     */
    override suspend fun login(email: String, password: String): Response<Unit, SupabaseErrors> {
        return supabaseRequest {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            return Response.Success(Unit)
        }
    }

    /**
     * Retrieves the profile information of the currently logged-in user.
     *
     * @return A [Result] containing the [ProfileModel] if successful,
     *         or an [Errors] object if an error occurred.
     */
    override suspend fun getProfileInfo(): Response<ProfileModel, SupabaseErrors> = supabaseRequest {
        // user is already logged in (try to get profile info from auth)
        val profileInfoFromAuth = supabaseClient.auth.currentUserOrNull()
        if (profileInfoFromAuth == null) {
            Logger.logError(message = "profileInfoFromAuth is null")
            return@supabaseRequest Response.Error(SupabaseErrors.PostgrestException)
        }
        val apiResponse = supabaseClient
            .from("users")
            .select {
                filter {
                    eq("auth_id", profileInfoFromAuth.id)
                }
            }
            .decodeSingleOrNull<ProfileEntity>()

        if (apiResponse == null) {
            Logger.logError(message = "apiResponse is null")
            return@supabaseRequest Response.Error(SupabaseErrors.PostgrestException)
        } else {
            return@supabaseRequest Response.Success(apiResponse.toModel())
        }
    }

}
