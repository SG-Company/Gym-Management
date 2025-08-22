package com.sotsap.apps.gymmanagement.core.framework.components.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.sotsap.apps.gymmanagement.core.framework.dimens.GymDimens

/**
 * A composable function that displays a button with a specific style and text.
 *
 * @param modifier The modifier to be applied to the button.
 * @param style The style of the button. Defaults to [GymButtonStyles.Primary].
 * @param text The text to be displayed on the button.
 * @param onClick The lambda to be executed when the button is clicked.
 * @param enabled Whether the button is enabled or not. Defaults to true.
 */
@Composable
fun GymButton(
    modifier: Modifier = Modifier,
    style: GymButtonStyles = GymButtonStyles.Primary,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    when (style) {
        GymButtonStyles.Primary -> GymButtonPrimary(
            modifier = modifier,
            text = text,
            onClick = onClick,
            enabled = enabled
        )
        else -> {}
    }
}

/**
 * A composable function that displays a primary button with a specific style and text.
 * This button is typically used for the main call to action on a screen.
 * It fills the width of its parent and has rounded corners.
 *
 * @param modifier The modifier to be applied to the button. Defaults to [Modifier].
 * @param text The text to be displayed on the button.
 * @param onClick The lambda to be executed when the button is clicked.
 * @param enabled A boolean indicating whether the button is enabled or not. Defaults to `true`.
 */
@Composable
private fun GymButtonPrimary(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = GymDimens.paddingThirty)
            .padding(bottom = GymDimens.paddingTwenty),
        enabled = enabled,
        onClick = onClick,
        shape = RoundedCornerShape(size = GymDimens.paddingTwenty)
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = GymDimens.paddingFive),
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            )
        )
    }
}