package com.sotsap.apps.gymmanagement.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.backhandler.PredictiveBackHandler
import com.sotsap.apps.gymmanagement.core.utilities.Logger
import kotlinx.coroutines.CancellationException

/**
 * __Android back button handler__
 *
 * A composable function that handles the back button press.
 *
 * @param action The action to be performed when the back button is pressed.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun handleOnBack(action: () -> Unit) {
    BackHandler(
        enabled = true,
        onBack = action
    )
}

/**
 * __Android back button handler__
 *
 * __Requirements:__
 *
 * In order to work properly, you have to set manually the
 * <code>
 *
 *     android:enableOnBackInvokedCallback="true"
 * </code>
 *
 * to __AndroidManifest.xml__ at your __composeApp/androidMain__ folder.
 *
 * A composable function that handles predictive back gestures.
 *
 * This function listens for predictive back gesture events and invokes the provided [action]
 * when the gesture is completed. It also provides a callback [onProgressCallback] to track
 * the progress of the back gesture.
 *
 * The function handles potential [CancellationException] that might occur during the progress collection.
 * If a [CancellationException] happens, it logs an error and still executes the [action].
 *
 * @param action The action to be performed when the predictive back gesture is completed.
 * @param onProgressCallback A callback function that receives the progress of the predictive back gesture (a Float between 0.0 and 1.0).
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun handleOnPredictiveBack(
    action: () -> Unit,
    onProgressCallback: (progress: Float) -> Unit = { _ -> }
) {
    PredictiveBackHandler(
        enabled = true
    ) { progress ->
        try {
            progress.collect { value ->
                onProgressCallback(value.progress)
            }
            action()
        } catch (ex: CancellationException) {
            Logger.error<Logger.NoClassProvider>(
                message = "PredictiveBackHandler: ${ex.message}",
                tag = "PredictiveBackHandler"
            )
            action()
        }
    }
}