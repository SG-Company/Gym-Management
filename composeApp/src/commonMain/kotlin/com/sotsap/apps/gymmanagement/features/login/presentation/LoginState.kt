package com.sotsap.apps.gymmanagement.features.login.presentation

import org.jetbrains.compose.resources.StringResource

/**
 * Represents the state of the login screen.
 *
 * This data class encapsulates all the information needed to render the login UI,
 * including loading indicators, localized text, and error messages.
 *
 * @property progress A boolean indicating whether a login operation is currently in progress.
 *                    `true` if loading, `false` otherwise.
 * @property wordings An instance of [Wordings] containing the localized strings for the UI elements.
 * @property error An instance of [Error] representing any validation or general login errors.
 */
data class LoginState(
    val progress: Boolean = false,
    val wordings: Wordings,
    val error: Error = Error()
) {

    /**
     * Represents the localized strings used in the login screen.
     *
     * @property header The resource ID for the main heading text.
     * @property subtitle The resource ID for the secondary text or slogan.
     * @property emailPlaceholder The resource ID for the email input field placeholder.
     * @property passwordPlaceholder The resource ID for the password input field placeholder.
     * @property loginButton The resource ID for the login button text.
     */
    data class Wordings(
        val header: StringResource,
        val subtitle: StringResource,
        val emailPlaceholder: StringResource,
        val passwordPlaceholder: StringResource,
        val loginButton: StringResource
    )

    /**
     * Represents the error state for the login screen.
     *
     * This data class holds optional string resources for error messages related to
     * email, password, or general login issues. If a field is null, it means there
     * is no error associated with that specific input or action.
     *
     * @property email A [StringResource] representing an error message for the email input, or null if no error.
     * @property password A [StringResource] representing an error message for the password input, or null if no error.
     * @property login A [StringResource] representing a general login error message, or null if no error.
     */
    data class Error(
        val email: StringResource? = null,
        val password: StringResource? = null,
        val login: StringResource? = null
    )

}
