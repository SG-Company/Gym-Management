package com.sotsap.apps.gymmanagement.features.login.data

import com.sotsap.apps.gymmanagement.core.models.Result
import com.sotsap.apps.gymmanagement.core.network.supabase.supabaseClient
import com.sotsap.apps.gymmanagement.features.login.domain.LoginRepository
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.exceptions.HttpRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException

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
    override suspend fun login(email: String, password: String): Result<Unit, LoginRepository.Errors> {
        return try {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            Result.Success(Unit)
        } catch (_: AuthRestException) {
            Result.Error(LoginRepository.Errors.AuthRest)
        } catch (_: HttpRequestTimeoutException) {
            Result.Error(LoginRepository.Errors.Timeout)
        } catch (_: HttpRequestException) {
            Result.Error(LoginRepository.Errors.Request)
        }
    }

}
