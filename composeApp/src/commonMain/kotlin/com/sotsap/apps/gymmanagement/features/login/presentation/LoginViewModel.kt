package com.sotsap.apps.gymmanagement.features.login.presentation

import com.sotsap.apps.gymmanagement.core.extensions.isNotEmail
import com.sotsap.apps.gymmanagement.core.lifecycle.BaseViewModel
import com.sotsap.apps.gymmanagement.core.models.Response
import com.sotsap.apps.gymmanagement.core.models.ifError
import com.sotsap.apps.gymmanagement.core.models.ifSuccess
import com.sotsap.apps.gymmanagement.core.models.ifSuccessAndThen
import com.sotsap.apps.gymmanagement.core.utilities.Logger
import com.sotsap.apps.gymmanagement.features.login.domain.LoginRepository
import gymmanagement.composeapp.generated.resources.Res
import gymmanagement.composeapp.generated.resources.loginButtonText
import gymmanagement.composeapp.generated.resources.loginEmailInputFieldErrorEmailTemplate
import gymmanagement.composeapp.generated.resources.loginEmailInputFieldErrorEmpty
import gymmanagement.composeapp.generated.resources.loginEmailLabel
import gymmanagement.composeapp.generated.resources.loginHeaderSubtitle
import gymmanagement.composeapp.generated.resources.loginHeaderTitle
import gymmanagement.composeapp.generated.resources.loginPasswordLabel
import org.jetbrains.compose.resources.StringResource

/**
 * ViewModel for the Login screen.
 *
 * This ViewModel manages the state and events related to the login process.
 * It extends [BaseViewModel] to handle common ViewModel functionalities.
 *
 * @property loginRepository The repository responsible for handling login-related operations.
 *
 * @see BaseViewModel
 * @see LoginState
 * @see LoginEvents
 */
class LoginViewModel(
    private val loginRepository: LoginRepository
): BaseViewModel<LoginState, LoginEvents>() {

    companion object {
        private const val TAG_LOGIN = "Login"
    }

    /**
     * Handles the change in the email input field.
     *
     * This function is called whenever the value in the email input field changes.
     * It validates the new email value and updates the state accordingly.
     *
     * If the new value is empty, the login button is disabled and an error message
     * for an empty email field is displayed.
     *
     * If the new value is not a valid email format, the login button is disabled
     * and an error message for an invalid email template is displayed.
     *
     * @param newValue The new string value entered in the email input field.
     */
    fun onEmailChange(newValue: String) {
        var loginButtonEnabled: Boolean
        var emailErrorText: StringResource?

        if (newValue.isEmpty()) {
            loginButtonEnabled = false
            emailErrorText = Res.string.loginEmailInputFieldErrorEmpty
        }else if (newValue.isNotEmail()) {
            loginButtonEnabled = false
            emailErrorText = Res.string.loginEmailInputFieldErrorEmailTemplate
        } else {
            loginButtonEnabled = true
            emailErrorText = null
        }
        update { state ->
            state.copy(
                loginButtonEnabled = loginButtonEnabled,
                emailErrorText = emailErrorText
            )
        }
    }

    /**
     * Attempts to log in the user with the provided email and password.
     * Launches a coroutine to perform the login operation asynchronously.
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     */
    fun onLogin(email: String, password: String) = launch(tag = TAG_LOGIN) { login(email, password) }

    /**
     * Logs in the user with the given email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     */
    suspend fun login(email: String, password: String) {
        update { it.copy(progress = true) }
        when (loginRepository.login(email, password)) {
            is Response.Success -> {
                update { it.copy(progress = false) }
                update(newEvent = LoginEvents.OnLogin)
            }
            is Response.Error -> {
                update { it.copy(progress = false) }
            }
        }
    }

    /**
     * Initializes the state of the ViewModel by setting the initial state explicitly.
     * @return The initial state of the ViewModel.
     */
    override suspend fun initialState() = LoginState(
        progress = false,
        headerTitleText = Res.string.loginHeaderTitle,
        headerSubtitleText = Res.string.loginHeaderSubtitle,
        emailLabelText = Res.string.loginEmailLabel,
        passwordLabelText = Res.string.loginPasswordLabel,
        loginButtonText = Res.string.loginButtonText,
        loginButtonEnabled = false,
        emailErrorText = null,
        passwordErrorText = null
    )

}