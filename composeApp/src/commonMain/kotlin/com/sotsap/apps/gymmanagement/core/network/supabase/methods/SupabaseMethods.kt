package com.sotsap.apps.gymmanagement.core.network.supabase.methods

import com.sotsap.apps.gymmanagement.core.models.Response
import com.sotsap.apps.gymmanagement.core.network.supabase.models.SupabaseErrors
import com.sotsap.apps.gymmanagement.core.utilities.Logger
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.postgrest.exception.PostgrestRestException
import io.ktor.client.plugins.HttpRequestTimeoutException

/**
 * Executes a Supabase request and handles potential exceptions.
 *
 * This function wraps a suspendable action that performs a Supabase operation.
 * It catches common Supabase exceptions like `PostgrestRestException` and `HttpRequestTimeoutException`,
 * logs them, and converts them into a standardized `Response.Error` object.
 *
 * @param T The type of the successful response data.
 * @param E The type of the error, which must be a subtype of `SupabaseErrors`.
 * @param action A suspendable lambda function that executes the Supabase request and returns a `Response<T, E>`.
 * @return A `Response<T, E>` which can be either `Response.Success<T>` or `Response.Error<E>`.
 */
@Suppress("UNCHECKED_CAST")
suspend inline fun <T, E: SupabaseErrors> supabaseRequest(
    action: suspend () -> Response<T, E>,
): Response<T, E> {
    try {
        return when(val response = action()) {
            is Response.Success -> response
            is Response.Error -> response
        }
    } catch (postgrest: PostgrestRestException) {
        Logger.logError(
            message = postgrest.message ?: "Unknown error",
            tag = "PostgrestRestException"
        )
        val error = Response.Error(
            error = SupabaseErrors.PostgrestException as E,
            code = postgrest.statusCode,
            message = postgrest.message,
            throwable = postgrest.cause
        )
        return error
    } catch (timeout: HttpRequestTimeoutException) {
        Logger.logError(
            message = timeout.message ?: "Unknown error",
            tag = "HttpRequestTimeoutException"
        )
        val error = Response.Error(
            error = SupabaseErrors.RequestTimeout as E,
            message = timeout.message,
            throwable = timeout.cause
        )
        return error
    } catch (request: HttpRequestException) {
        Logger.logError(
            message = request.message ?: "Unknown error",
            tag = "HttpRequestException"
        )
        val error = Response.Error(
            error = SupabaseErrors.RequestTimeout as E,
            message = request.message,
            throwable = request.cause
        )
        return error
    } catch (auth: AuthRestException) {
        Logger.logError(
            message = auth.message ?: "Unknown error",
            tag = "AuthRestException"
        )
        val error = Response.Error(
            error = SupabaseErrors.RequestTimeout as E,
            code = auth.statusCode,
            message = auth.message,
            throwable = auth.cause
        )
        return error
    }
}
