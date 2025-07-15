package com.sotsap.apps.gymmanagement.core.utilities

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase

/**
 * `Logger` provides a centralized logging mechanism with severity levels (debug, info, warning, error).
 *
 * It can be extended to support various destinations (console, file, etc.).
 *
 * @author Sotiris Sapakos
 * @since 1.0.0
 */
object Logger {

    /**
     * If programmer does not provide a class, then this class will be provided in logs so class provider can be identified.
     * @author Sotiris Sapakos
     * @since 1.0.0
     */
    class NoClassProvider

    /**
     * Represents the different types of log entries.
     *
     * @property DEBUG Information useful during development.
     * @property WARN Indicates potential issues that don't currently prevent the application from running correctly.
     * @property ERROR Indicates a problem that is preventing the application from functioning correctly.
     */
    enum class Type { DEBUG, WARN, ERROR }


    /**
     * Generates a formatted log string based on the provided message and optional tag.
     *
     * This function is intended to be used internally for creating log entries with a consistent format.
     * It constructs a string that includes the class name, an optional tag, and the log message.
     *
     * @param message The main log message.
     * @param tag An optional tag to include in the log string. If provided, it will be capitalized and
     *            enclosed in brackets.
     * @param T The type of the object for which the log string is being generated. The class name of this type
     *          will be included in the log string.
     * @return The formatted log string.
     */
    inline fun <reified T> templatedLogString(message: String, type: Type, tag: String? = null): String {
        val className = T::class.simpleName
        val tagString = if (tag.isNullOrEmpty()) "-" else tag.toUpperCase(Locale.current)
        val typeString = when (type) {
            Type.DEBUG -> "DEBUG"
            Type.WARN -> "WARN"
            Type.ERROR -> "ERROR"
        }
        val templatedString = "$typeString($tagString) <$className> $message"
        return templatedString
    }

    /**
     * Prints a debug message to the console, including the class name of the object and an optional tag.
     *
     * @param message The debug message to print.
     * @param tag An optional tag to include in the debug message.
     * @see print
     * @receiver Any object on which to call the debug function.
     * @reified T The type of the object, used to get the simple class name.
     */
    inline fun <reified T> debug(message: String, tag: String? = null) {
        println(
            templatedLogString<T>(
                message = message,
                tag = tag,
                type = Type.DEBUG
            )
        )
    }

    /**
     * Logs a warning message associated with the calling object's class.
     *
     * This function prints a warning message to the standard output stream,
     * including the class name of the calling object and an optional tag.
     *
     * @param message The warning message to be logged.
     * @param tag An optional tag to provide additional context for the warning. If provided it
     * will be shown between brackets.
     * @see print
     */
    inline fun <reified T> warn(message: String, tag: String? = null) {
        println(
            templatedLogString<T>(
                message = message,
                tag = tag,
                type = Type.WARN
            )
        )
    }

    /**
     * Prints an error message to the console, including the class name and an optional tag.
     *
     * @param message The error message to be printed.
     * @param tag An optional tag to categorize the error. Defaults to null.
     * @param T The type of the object calling this function. This will be used to determine the class name.
     */
    inline fun <reified T> error(message: String, tag: String? = null) {
        println(
            templatedLogString<T>(
                message = message,
                tag = tag,
                type = Type.ERROR
            )
        )
    }

    /**
     * Prints a log message with the specified type and tag.
     *
     * @param message The message to be logged.
     * @param type The type of the log message (DEBUG, WARN, ERROR). Defaults to DEBUG.
     * @param tag An optional tag to be associated with the log message.
     * @param T The type of the object that this function is called on. Reified to allow access to the type information.
     * @receiver The object on which this extension function is called.
     *
     * @see Type
     * @see debug
     * @see warn
     * @see error
     */
    inline fun <reified T> logger(message: String, type: Type = Type.DEBUG, tag: String? = null) {
        when (type) {
            Type.DEBUG -> debug<T>(message, tag)
            Type.WARN -> warn<T>(message, tag)
            Type.ERROR -> error<T>(message, tag)
        }
    }

    /**
     * Logs a message with the specified type and optional tag.
     *
     * @param message The message to log.
     * @param type The type of log message (e.g., DEBUG, WARN, ERROR). Defaults to [Type.DEBUG].
     * @param tag An optional tag to associate with the log message. If null, a default tag may be used internally.
     */
    fun log(message: String, type: Type = Type.DEBUG, tag: String? = null) {
        when (type) {
            Type.DEBUG -> debug<NoClassProvider>(message, tag)
            Type.WARN -> warn<NoClassProvider>(message, tag)
            Type.ERROR -> error<NoClassProvider>(message, tag)
        }
    }

    /**
     * Logs a debug message without requiring a specific class context.
     *
     * This function provides a convenient way to log debug messages when the context of a
     * specific class is not necessary. It internally calls the [debug] function with a
     * placeholder class to ensure consistent formatting.
     *
     * @param message The debug message to log.
     * @param tag An optional tag to provide additional context for the debug message. If provided it
     * will be shown between brackets.
     * @see debug
     */
    fun logDebug(message: String, tag: String? = null) = debug<NoClassProvider>(message, tag)

    /**
     * Logs a warning message without specifying a class provider.
     *
     * This function simplifies the process of logging a warning by defaulting to a no-class
     * provider scenario. It's useful for quick warning logs where specifying the calling
     * class isn't necessary.
     *
     * @param message The warning message to be logged.
     * @param tag An optional tag to provide additional context for the warning.
     * @see warn
     */
    fun logWarn(message: String, tag: String? = null) = warn<NoClassProvider>(message, tag)

    /**
     * Logs an error message without a specific class context.
     *
     * This function simplifies error logging by using the [error] function with the
     * [NoClassProvider] as the default class. It's useful when you want to log an error
     * but don't need to associate it with a specific class.
     *
     * @param message The error message to be logged.
     * @param tag An optional tag to provide additional context to the error message.
     * @see error
     */
    fun logError(message: String, tag: String? = null) = error<NoClassProvider>(message, tag)

}

// ? ===============================================================================================
// ? Extensions
// ? ===============================================================================================

/**
 * Logs a debug message if the specified condition is true.
 *
 * This function evaluates the provided [condition] and, if it returns `true`, logs the given
 * [message] as a debug message. The log message includes the class name of the specified type [T]
 * and an optional [tag]. If the condition is `false`, no log message is generated.
 *
 * @param T The type of the object associated with the log message. The class name of this type
 *          will be included in the log output.
 * @param tag An optional tag to provide additional context to the log message.
 * @param message The debug message to log.
 * @param condition A lambda expression that returns a boolean value. If this lambda returns `true`,
 *                  the debug message will be logged; otherwise, no message will be logged.
 */
inline fun <reified T> debugIf(
    tag: String? = null,
    message: String,
    condition: () -> Boolean
) {
    if (condition.invoke()) {
        Logger.debug<T>(message, tag)
    }
}

/**
 * Logs a warning message if the specified condition is met.
 *
 * This function checks the result of the [condition] lambda. If the condition evaluates to `true`,
 * a warning message is logged using the specified [message] and optional [tag]. The log entry will
 * be associated with the class [T]. If the condition is `false`, no action is taken.
 *
 * @param T The type to associate with the log message. The class name of [T] will be included in the log output.
 * @param tag An optional tag to provide additional context for the warning message.
 * @param message The warning message to log.
 * @param condition A lambda expression that returns a boolean value. If it returns `true`, the warning message is logged.
 */
inline fun <reified T> warnIf(
    tag: String? = null,
    message: String,
    condition: () -> Boolean
) {
    if (condition.invoke()) {
        Logger.warn<T>(message, tag)
    }
}

/**
 * Logs an error message if the specified condition is met.
 *
 * This function evaluates the given [condition]. If the condition is true, it logs an error
 * message with the provided [message] and optional [tag]. The error message is associated with
 * the specified type [T]. If the condition is false, no error message is logged.
 *
 * @param T The type associated with the error message. The class name of this type will be
 *          included in the log output.
 * @param tag An optional tag to provide additional context to the error message.
 * @param message The error message to log if the condition is true.
 * @param condition A lambda expression that returns a boolean. If this expression returns true,
 *                  the error message will be logged.
 */
inline fun <reified T> errorIf(
    tag: String? = null,
    message: String,
    condition: () -> Boolean
) {
    if (condition.invoke()) {
        Logger.error<T>(message, tag)
    }
}