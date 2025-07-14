package com.sotsap.apps.gymmanagement.core.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sotsap.apps.gymmanagement.core.lifecycle.BaseViewModel
import com.sotsap.apps.gymmanagement.core.utilities.debugIf
import org.koin.compose.viewmodel.koinViewModel

/**
 * A composable function that provides a base structure for screens in the application.
 *
 * This function handles the common setup for a screen, including:
 * - Injecting a [BaseViewModel] using Koin.
 * - Observing the state and event flows from the ViewModel.
 * - Providing the current state, event, and ViewModel instance to the `content` composable.
 * - Optionally enabling debug logs for ViewModel injection, state changes, and event emissions.
 *
 * @param S The type of the state emitted by the ViewModel.
 * @param E The type of the event emitted by theViewModel.
 * @param V The type of the [BaseViewModel] associated with this screen.
 * @param enableLogs A boolean flag to enable or disable debug logging. Defaults to `false`.
 * @param content A composable lambda that defines the UI content of the screen.
 *                It receives the current state (`S?`), event (`E?`), and the ViewModel instance (`V`).
 */
@Composable
inline fun <reified S, reified E, reified V: BaseViewModel<S, E>> BaseScreen(
    enableLogs: Boolean = false,
    crossinline content: @Composable (state: S?, event: E?, viewModel: V) -> Unit
) {

    val tagInjectViewModel = "InjectedViewModel"
    val tagStateProvider = "StateProvider"
    val tagEventProvider = "EventProvider"

    val vm = koinViewModel<V>()
    debugIf<ViewModel>(
        tag = tagInjectViewModel,
        message = "Injected ViewModel: ${ViewModel::class.simpleName}",
        condition = { enableLogs }
    )

    val state by vm.state.collectAsStateWithLifecycle()
    debugIf<S>(
        tag = tagStateProvider,
        message = "new state: $state",
        condition = { enableLogs && state != null }
    )

    val event by vm.event.collectAsStateWithLifecycle()
    debugIf<E>(
        tag = tagEventProvider,
        message = "new Event: $event",
        condition = { enableLogs && event != null }
    )

    content(state, event, vm)
}