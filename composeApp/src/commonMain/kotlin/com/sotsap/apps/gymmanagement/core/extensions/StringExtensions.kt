package com.sotsap.apps.gymmanagement.core.extensions

/**
 * Checks if the string is a valid email address.
 *
 * This function uses a regular expression to validate the email format.
 * It considers a string as a valid email if it:
 * - Is not null or blank.
 * - Matches the pattern: `^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$`
 *   - Starts with one or more alphanumeric characters, plus signs, underscores, periods, or hyphens.
 *   - Followed by an "@" symbol.
 *   - Followed by one or more alphanumeric characters, periods, or hyphens (domain name).
 *   - Followed by a period.
 *   - Ends with two or more alphabetic characters (top-level domain).
 *
 * @return `true` if the string is a valid email address, `false` otherwise.
 */
fun String?.isEmail(): Boolean {
    if (isNullOrBlank()) return false
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    return emailRegex.matches(this)
}

/**
 * Checks if a string is not a valid email address.
 *
 * @return `true` if the string is not a valid email address, `false` otherwise.
 */
fun String?.isNotEmail() = !isEmail()