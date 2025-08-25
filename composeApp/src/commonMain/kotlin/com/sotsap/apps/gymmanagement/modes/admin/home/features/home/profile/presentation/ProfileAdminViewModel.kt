package com.sotsap.apps.gymmanagement.modes.admin.home.features.home.profile.presentation

import com.sotsap.apps.gymmanagement.core.lifecycle.BaseViewModel
import com.sotsap.apps.gymmanagement.core.models.Response
import com.sotsap.apps.gymmanagement.core.utilities.Logger
import com.sotsap.apps.gymmanagement.features.login.domain.LoginRepository

class ProfileAdminViewModel(
    private val repository: LoginRepository
): BaseViewModel<ProfileAdminState, ProfileAdminEvents>() {

    fun onLogoutClick() = launch {

    }

    /**
     * Initializes the state of the ViewModel by setting the initial state explicitly.
     * @return The initial state of the ViewModel.
     */
    override suspend fun initialState(): ProfileAdminState {
        return when (val response = repository.getProfileInfo()) {
            is Response.Error -> {
                Logger.logError(
                    message = response.error.toString(),
                )
                ProfileAdminState()
            }
            is Response.Success -> ProfileAdminState(
                progress = false,
                profile = response.data
            )
        }
    }

}