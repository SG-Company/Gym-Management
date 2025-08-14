package com.sotsap.apps.gymmanagement.features.login.presentation

import com.sotsap.apps.gymmanagement.core.lifecycle.BaseViewModel
import com.sotsap.apps.gymmanagement.core.models.ifError
import com.sotsap.apps.gymmanagement.core.models.ifSuccess
import com.sotsap.apps.gymmanagement.features.login.domain.LoginRepository
import gymmanagement.composeapp.generated.resources.Res
import gymmanagement.composeapp.generated.resources.loginButtonText
import gymmanagement.composeapp.generated.resources.loginEmailLabel
import gymmanagement.composeapp.generated.resources.loginHeaderSubtitle
import gymmanagement.composeapp.generated.resources.loginHeaderTitle
import gymmanagement.composeapp.generated.resources.loginPasswordLabel

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
        loginRepository
            .login(email, password)
            .ifSuccess {
                update { it.copy(progress = false) }
            }
            .ifError {
                update { it.copy(progress = false) }
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
        loginButtonText = Res.string.loginButtonText
    )

}