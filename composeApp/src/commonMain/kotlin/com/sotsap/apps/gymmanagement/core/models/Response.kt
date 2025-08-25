package com.sotsap.apps.gymmanagement.core.models

/**
 * Base interface for all error responses.
 *
 * This interface is used to define the contract for error responses in the application.
 * It is sealed, meaning that all implementations must be defined in the same file.
 * This allows the compiler to perform exhaustive checks when handling error responses.
 *
 * Example usage:
 * ```kotlin
 * sealed interface MyError : ResponseError {
 *     data class NetworkError(val message: String) : MyError
 *     data class ServerError(val code: Int) : MyError
 * }
 * ```
 */
interface ResponseError

/**
 * Represents a response from an operation, which can either be a success or an error.
 * This is a sealed interface, meaning all possible subtypes are known at compile time.
 *
 * @param T The type of data expected in a successful response.
 * @param E The type of error expected in an error response, constrained to be a subtype of [ResponseError].
 */
sealed interface Response<out T, out E: ResponseError> {

    /**
     * Represents a successful response.
     *
     * @param T The type of the data.
     * @property data The data of the response.
     */
    data class Success<out T>(val data: T): Response<T, Nothing>

    /**
     * Represents an error response.
     *
     * @param E The type of the error, which must implement [ResponseError].
     * @property error The specific error object.
     * @property code Optional HTTP status code associated with the error.
     * @property message Optional human-readable message describing the error.
     * @property throwable Optional underlying throwable that caused this error.
     */
    data class Error<out E: ResponseError>(
        val error: E,
        val code: Int? = null,
        val message: String? = null,
        val throwable: Throwable? = null
    ): Response<Nothing, E>

}

// ? ===============================================================================================
// ? Extension methods for Response
// ? ===============================================================================================

/** Returns true if this is a Success */
val Response<*, *>.isSuccess: Boolean
    get() = this is Response.Success

/** Returns true if this is an Error */
val Response<*, *>.isError: Boolean
    get() = this is Response.Error

/** Extracts data or null */
fun <T> Response<T, *>.getOrNull(): T? =
    (this as? Response.Success)?.data

/** Extracts error or null */
fun <E : ResponseError> Response<*, E>.errorOrNull(): E? =
    (this as? Response.Error)?.error

/** Maps the success value if present */
inline fun <T, R, E : ResponseError> Response<T, E>.map(
    transform: (T) -> R
): Response<R, E> = when (this) {
    is Response.Success -> Response.Success(transform(data))
    is Response.Error -> this
}

/** Maps the error value if present */
inline fun <T, E : ResponseError, F : ResponseError> Response<T, E>.mapError(
    transform: (E) -> F
): Response<T, F> = when (this) {
    is Response.Success -> this
    is Response.Error -> Response.Error(
        error = transform(error),
        message = message,
        code = code
    )
}

/** Fold into a single value (like Either) */
inline fun <T, R, E : ResponseError> Response<T, E>.fold(
    onSuccess: (T) -> R,
    onError: (E, String?, Int?) -> R
): R = when (this) {
    is Response.Success -> onSuccess(data)
    is Response.Error -> onError(error, message, code)
}

/** Run side-effect only if success */
inline fun <T, E : ResponseError> Response<T, E>.onSuccess(
    action: (T) -> Unit
): Response<T, E> = apply {
    if (this is Response.Success) action(data)
}

/** Run side-effect only if error */
inline fun <T, E : ResponseError> Response<T, E>.onError(
    action: (E, String?, Int?) -> Unit
): Response<T, E> = apply {
    if (this is Response.Error) action(error, message, code)
}