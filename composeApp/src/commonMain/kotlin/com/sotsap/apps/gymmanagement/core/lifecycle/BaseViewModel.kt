package com.sotsap.apps.gymmanagement.core.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sotsap.apps.gymmanagement.core.utilities.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * A base class for ViewModels that provides a structured way to manage state and events.
 *
 * This class uses Kotlin coroutines and [StateFlow] to handle asynchronous operations and UI updates.
 * It defines a generic state type [S] and event type [E] to be specified by subclasses.
 *
 * @param S The type of the state managed by this ViewModel.
 * @param E The type of the events emitted by this ViewModel.
 * @param initialize A boolean flag indicating whether the ViewModel should initialize its state immediately upon creation.
 *                   Defaults to `true`. If `true`, the [initialState] function will be called and its result
 *                   will be used to update the ViewModel's state.
 *
 * @property state A [StateFlow] that exposes the current state of the ViewModel. UI components can observe this flow
 *                 to react to state changes.
 * @property event A [StateFlow] that exposes events emitted by the ViewModel. UI components can observe this flow
 *                 to react to one-time events (e.g., navigation, showing a toast).
 */
abstract class BaseViewModel<S, E>(initialize: Boolean = true): ViewModel() {

    private val tagInitializationJob = "Job-Initialization"

    /**
     * The current state of the view model.
     *
     * It's a [MutableStateFlow] that holds the current state of type [State],
     * allowing observation and updates to the UI. The initial value is `null`,
     * indicating that no state has been set yet.
     */
    private val _state = MutableStateFlow<S?>(null)

    /**
     * Holds the current state of the view model.
     */
    val state: StateFlow<S?> = _state.asStateFlow()

    /**
     * A [MutableStateFlow] that holds the current event of the [BaseViewModel].
     *
     * This is an internal property and should only be updated by the [BaseViewModel] itself.
     *
     * The value is nullable, and it is `null` if no event has been emitted yet.
     */
    private val _event = MutableStateFlow<E?>(null)

    /**
     * A [MutableStateFlow] that holds the current event of the [BaseViewModel].
     *
     * This is an internal property and should only be updated by the [BaseViewModel] itself.
     *
     * The value is nullable, and it is `null` if no event has been emitted yet.
     */
    val event: StateFlow<E?> = _event.asStateFlow()

    /**
     * The [Job] used to manage the lifecycle of coroutines launched by this ViewModel.
     */
    private var job: Job = Job()

    /**
     * A map of [Job]s used to manage the lifecycle of coroutines that may be launched from the ui layer.
     *
     * This map holds [Job] instances identified by a string key. This is useful for tracking and
     * potentially canceling coroutines individually.
     */
    private val jobs = mutableMapOf<String, Job>()

    init {
        if (initialize) {
            launch(
                tag = tagInitializationJob,
                dispatcher = Dispatchers.Main,
            ) {
                val initialState = initialState()
                update(newState = initialState)
            }
        }
    }

    /**
     * Updates the current state of the ViewModel.
     *
     * This function takes an action as a lambda expression that receives the current state as input and returns a new state.
     * It then updates the [_state] LiveData with the new state resulting from applying the action to the current state.
     * The update is performed only if the current state is not null.
     *
     * @param action A lambda expression representing the action to be performed on the current state.
     *               It takes the current state as input and returns the new state.
     */
    protected fun update(action: (currentState: S) -> S) {
        _state.value?.let { currentState ->
            _state.value = action(currentState)
        }
    }

    /**
     * Updates the current state of the ViewModel with a new state.
     *
     * @param newState The new state to be set.
     */
    protected fun update(newState: S) {
        _state.value = newState
    }

    /**
     * Emits a new event to the event flow.
     *
     * This function is used to emit a new event of type [E] to the [_event] flow.
     * It uses [MutableStateFlow.tryEmit] to ensure thread-safe emission of the new event.
     *
     * @param newEvent The new event to be emitted.
     */
    protected fun update(newEvent: E) = _event.tryEmit(newEvent)

    /**
     * Stops a coroutine identified by the given [tag] if it is currently active.
     *
     * This function checks if a coroutine with the specified [tag] exists and is active in the [jobs] map.
     * If found, the coroutine is cancelled.
     *
     * @param tag The unique identifier of the coroutine to stop.
     */
    protected fun stopCoroutine(tag: String) {
        if (jobs.containsKey(tag)) {
            jobs[tag]?.cancel()
            jobs.remove(tag)
        }
    }

    /**
     * Launches a coroutine within the [viewModelScope] with a specific [tag].
     *
     * Manages coroutines, ensuring only one with a given [tag] runs at a time.
     * If a coroutine with the same tag is already running, it will be stopped before the new one starts.
     *
     * @param tag Unique identifier for the coroutine. Must not be empty.
     * @param dispatcher The [CoroutineDispatcher]. Defaults to [Dispatchers.IO].
     * @param action The suspend function to execute.
     *
     * @since 0.0.4
     */
    protected fun launch(
        tag: String = "",
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        action: suspend () -> Unit
    ) {
        // Tag should not be empty, so check it
        if (tag.isEmpty()) {
            Logger.error<BaseViewModel<S, E>>(
                tag = "launch coroutine",
                message = "Tag should not be empty"
            )
            return
        }
        // stop the running coroutine based on the tag
        stopCoroutine(tag)
        // search for the job with the same tag
        if (jobs.isNotEmpty() && jobs.containsKey(tag)) {
            Logger.error<BaseViewModel<S, E>>(
                tag = "launch coroutine",
                message = "Tag should not be identical to another job"
            )
            return
        }
        // launch the new coroutine
        jobs[tag] = viewModelScope.launch(dispatcher) { action() }
    }

    /**
     * Initializes the state of the ViewModel by setting the initial state explicitly.
     * @return The initial state of the ViewModel.
     */
    abstract suspend fun initialState(): S

}