package com.sotsap.apps.gymmanagement.features.login.presentation

import com.sotsap.apps.gymmanagement.core.lifecycle.BaseViewModel
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
 */
class LoginViewModel: BaseViewModel<LoginState, LoginIntents>() {

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

}