package com.sotsap.apps.gymmanagement.features.initial.presentation

import com.sotsap.apps.gymmanagement.core.lifecycle.BaseViewModel
import com.sotsap.apps.gymmanagement.core.models.Response
import com.sotsap.apps.gymmanagement.features.initial.domain.remote.InitialRepository

/**
 * ViewModel for the Initial screen, responsible for handling business logic and managing the UI state.
 * It extends [BaseViewModel] and uses [InitialState] to represent the UI state and [InitialEvent]
 * for handling user interactions or events.
 * @param initialRepository The repository responsible for fetching initial data.
 */
class InitialViewModel(
    private val initialRepository: InitialRepository
): BaseViewModel<InitialState, InitialEvent>() {

    companion object {
        private const val INITIALIZE = "Initialize"
    }

    init {
        initialize()
    }
    /**
     * Initializes the ViewModel. This function is called when the ViewModel is created.
     * It's a placeholder for any initialization logic that might be needed in the future.
     */
    private fun initialize() = launch(tag = INITIALIZE) {
        when (val response = initialRepository.isSessionActive()) {
            is Response.Success -> {
                when (response.data) {
                    true -> update(newEvent = InitialEvent.NavigateToHome)
                    false -> update(newEvent = InitialEvent.NavigateToLogin)
                }
            }
            is Response.Error -> { /* This request will never return an error */ }
        }
    }

    /**
     * Initializes the state of the ViewModel by setting the initial state explicitly.
     * @return The initial state of the ViewModel.
     */
    override suspend fun initialState() = InitialState()

}