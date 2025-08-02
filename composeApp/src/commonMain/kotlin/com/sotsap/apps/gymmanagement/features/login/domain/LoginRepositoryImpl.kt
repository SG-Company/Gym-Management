package com.sotsap.apps.gymmanagement.features.login.domain

import com.sotsap.apps.gymmanagement.core.models.Result
import com.sotsap.apps.gymmanagement.core.network.supabase.supabaseClient
import com.sotsap.apps.gymmanagement.features.login.data.LoginRepository
import com.sotsap.apps.gymmanagement.features.login.data.LoginRepository.Errors
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.ktor.client.plugins.HttpRequestTimeoutException

/**
 * Implementation of [LoginRepository] that handles user authentication.
 *
 * This class uses the Supabase client to perform login operations.
 */
class LoginRepositoryImpl: LoginRepository {

    /**
     * Logs in a user with the given email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return A [Result] indicating success ([Unit]) or an [Errors] object if login fails.
     */
    override suspend fun login(email: String, password: String): Result<Unit, Errors> {
        return try {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            Result.Success(Unit)
        } catch (rest: RestException) {
            if (rest is AuthRestException) {
                Result.Error(Errors.AuthRest)
            } else {
                Result.Error(Errors.Rest)
            }
        } catch (_: HttpRequestTimeoutException) {
            Result.Error(Errors.Timeout)
        } catch (_: HttpRequestException) {
            Result.Error(Errors.Request)
        }
    }


}