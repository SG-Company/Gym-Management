package com.sotsap.apps.gymmanagement.core.models

typealias GenericError = Error

/**
 * Represents an error that occurred during an operation.
 * This interface can be implemented by specific error types to provide more detailed information.
 */
interface Error

/**
 * Represents the result of an operation that can either succeed with data [D] or fail with an error [E].
 *
 * This is a sealed interface, meaning that all possible subtypes are defined within the same file.
 * This allows for exhaustive `when` expressions when handling results.
 *
 * The two subtypes are:
 * - [Success]: Represents a successful operation with the resulting data.
 * - [Error]: Represents a failed operation with an error object.
 *
 * @param D The type of the data returned upon success.
 * @param E The type of the error returned upon failure, must be a subtype of [Error].
 */
sealed interface Result<out D, out E: GenericError> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: GenericError>(val error: E): Result<Nothing, E>
}

// ? ===============================================================================================
// ? Extensions methods for Result
// ? ===============================================================================================

/**
 * Executes the given [action] if the [Result] is a [Result.Success].
 *
 * @param action The action to execute with the data of the [Result.Success].
 */
fun <D, E: GenericError> Result<D, E>.ifSuccess(action: (D) -> Unit): Result<D, E> {
    if (this is Result.Success) {
        action(data)
    }
    return this
}

/**
 * Executes the given [action] if the [Result] is an [Error].
 *
 * This function allows you to handle errors directly within a chain of operations without
 * the need for explicitly checking the result type. If the result is an error, the [action]
 * will be invoked with the error object. Otherwise, the [action] will not be executed.
 *
 * @param action The lambda to execute if the [Result] is an [Error]. It takes the error object as a parameter.
 */
fun <D, E: GenericError> Result<D, E>.ifError(action: (E) -> Unit): Result<D, E> {
    if (this is Result.Error) {
        action(error)
    }
    return this
}

/**
 * Executes the given [action] if the [Result] is a [Result.Success].
 *
 * This is a suspending version of [ifSuccess], allowing you to perform asynchronous operations within the [action].
 * The [action] will be executed with the data of the [Result.Success]. If this result is an Error, this function will
 * not perform anything.
 *
 * @param action The suspend action to execute with the data of the [Result.Success].
 * @return The original [Result] object, allowing for chaining.
 * @since 0.0.4
 */
suspend fun <D, A, E: GenericError> Result<D, E>.ifSuccessAndThen(
    action: suspend (D) -> Result<A, E>
): Result<A, E>? {
    if (this is Result.Success) {
        return action(data)
    }
    return null
}

/**
 * Executes the given [action] if the [Result] is a [Result.Success] and returns the result of that action.
 *
 * This function is useful for chaining operations where the success of one operation determines whether the next one should be executed.
 * If the result is a success, the [action] will be executed, and its result will be returned. Otherwise, the original
 * success result is not used directly and the new action result is returned.
 *
 * @param action The action to execute if the [Result] is a [Result.Success].
 * @return The [Result] returned by the [action].
 * @since 0.0.4
 */
suspend fun <D, E: GenericError> Result<D, E>.ifSuccessAndThen(
    action: suspend () -> Result<D, E>
): Result<D, E>? {
    if (this is Result.Success) {
        return action()
    }
    return null
}

/**
 * Executes the given [action] if the [Result] is an [Result.Error] and returns the result of that action.
 *
 * This function is useful for chaining operations where the error of one operation determines whether the next one should be executed.
 * If the result is an error, the [action] will be executed, and its result will be returned. Otherwise, the original
 * error result is not used directly and null is returned.
 *
 * @param action The action to execute if the [Result] is an [Result.Error].
 * @return The [Result] returned by the [action] if the original [Result] was an [Result.Error], null otherwise.
 * @since 0.0.4
 */
suspend fun <D, E: GenericError> Result<D, E>.ifErrorAndThen(
    action: suspend () -> Result<D, E>
): Result<D, E>? {
    if (this is Result.Error) {
        return action()
    }
    return null
}

/**
 * Executes the given [action] if the [Result] is a [Result.Error].
 *
 * This is a suspending version of [ifError], allowing you to perform asynchronous operations within the [action].
 * If the result is an error, the [action] will be executed with the error object. The result of that action will be returned.
 * Otherwise if this result is a Success, this function will not perform anything.
 *
 * @param action The suspend action to execute with the error object if the [Result] is an [Error].
 * @return The [Result] returned by the [action] if the [Result] is an [Error], `null` otherwise.
 * @since 0.0.4
 */
suspend fun <D, E: GenericError> Result<D, E>.ifErrorAndThen(
    action: suspend (E) -> Result<D, E>
): Result<D, E>? {
    if (this is Result.Error) {
        return action(error)
    }
    return null
}