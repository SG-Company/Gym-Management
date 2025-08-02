package com.sotsap.apps.gymmanagement.features.login.presentation

import com.sotsap.apps.gymmanagement.core.lifecycle.BaseViewModel
import com.sotsap.apps.gymmanagement.core.models.ifError
import com.sotsap.apps.gymmanagement.core.models.ifSuccess
import com.sotsap.apps.gymmanagement.core.utilities.Logger
import com.sotsap.apps.gymmanagement.features.login.data.LoginRepository
import gymmanagement.composeapp.generated.resources.LoginCTA
import gymmanagement.composeapp.generated.resources.LoginEmailPlaceholder
import gymmanagement.composeapp.generated.resources.LoginHeader
import gymmanagement.composeapp.generated.resources.LoginPasswordPlaceholder
import gymmanagement.composeapp.generated.resources.LoginSubtitle
import gymmanagement.composeapp.generated.resources.Res

/**
 * ViewModel for the Login screen.
 *
 * This ViewModel handles the business logic for the login process,
 * interacting with use cases and managing the UI state ([LoginState])
 * based on user actions ([LoginIntents]).
 *
 * It extends [BaseViewModel] to leverage common ViewModel functionalities.
 * @param loginRepository The repository responsible for handling login-related operations.
 */
class LoginViewModel(
    private val loginRepository: LoginRepository
): BaseViewModel<LoginState, LoginIntents>() {

    /**
     * Updates the username in the [LoginState].
     * @param username The new username.
     */
    fun onUsernameChange(username: String) {
        update { it.copy(username = username) }
    }

    /**
     * Updates the password in the [LoginState].
     * @param password The new password.
     */
    fun onPasswordChange(password: String) {
        update { it.copy(password = password) }
    }

    /**
     * Initiates the login process.
     *
     * This function calls the private `login()` function to handle the actual login logic.
     */
    fun onLogin() = login()

    /**
     * Initializes the state of the ViewModel by setting the initial state explicitly.
     * @return The initial state of the ViewModel.
     */
    override suspend fun initialState() = LoginState(
        progress = false,
        wordings = LoginState.Wordings(
            header = Res.string.LoginHeader,
            subtitle = Res.string.LoginSubtitle,
            emailPlaceholder = Res.string.LoginEmailPlaceholder,
            passwordPlaceholder = Res.string.LoginPasswordPlaceholder,
            loginButton = Res.string.LoginCTA
        ),
        error = LoginState.Error()
    )

    /**
     * Attempts to log in the user with the current username and password from the [LoginState].
     *
     * This function retrieves the email and password from the current state.
     * It then calls the `login` method of the [loginRepository].
     *
     * On a successful login:
     * - Sets the `progress` flag in the state to `false`.
     * - Dispatches the [LoginIntents.OnLogin] intent to signal a successful login.
     *
     * On a failed login:
     * - Logs an error message containing the error name.
     */
    private fun login() = launch(tag = TAG_LOGIN) {
        val email = state.value?.username ?: ""
        val password = state.value?.password ?: ""
        loginRepository
            .login(email = email, password = password)
            .ifSuccess {
                update { it.copy(progress = false) }
                update(LoginIntents.OnLogin)
            }
            .ifError {
                Logger.error<LoginViewModel>(message = it.name)
            }
    }


    companion object {

        const val TAG_LOGIN = "TAG_LOGIN"

    }

}
