package com.sotsap.apps.gymmanagement.features.login.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.sotsap.apps.gymmanagement.core.compose.BaseScreen
import com.sotsap.apps.gymmanagement.core.framework.dimens.GymDimens
import com.sotsap.apps.gymmanagement.core.framework.extensions.Insets
import com.sotsap.apps.gymmanagement.core.framework.extensions.paddingInsets
import gymmanagement.composeapp.generated.resources.Res
import gymmanagement.composeapp.generated.resources.iconEmail
import gymmanagement.composeapp.generated.resources.iconPasswordHide
import gymmanagement.composeapp.generated.resources.iconPasswordLock
import gymmanagement.composeapp.generated.resources.iconPasswordShow
import gymmanagement.composeapp.generated.resources.loginButtonText
import gymmanagement.composeapp.generated.resources.loginEmailLabel
import gymmanagement.composeapp.generated.resources.loginHeaderSubtitle
import gymmanagement.composeapp.generated.resources.loginHeaderTitle
import gymmanagement.composeapp.generated.resources.loginPasswordLabel
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Composable function for the Login Screen.
 *
 * This screen handles the user login process. It utilizes [BaseScreen] to manage
 * its state ([LoginState]), events ([LoginEvents]), and ViewModel ([LoginViewModel]).
 *
 * Currently, it displays a simple "Hello from login screen" text.
 * @param insets The padding values representing the insets of the screen.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    insets: PaddingValues
) = BaseScreen<LoginState, LoginEvents, LoginViewModel> { state, event, viewModel ->

    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    LoginContent(
        modifier = modifier,
        insets = insets,
        state = state,
        initialEmail = email.value,
        initialPassword = password.value,
        onEmailChange = { email.value = it },
        onPasswordChange = { password.value = it },
        onLogin = { viewModel.onLogin(email.value, password.value) }
    )
}

/**
 * Composable function for the main content of the login screen.
 *
 * This function arranges the different UI elements that constitute the login screen.
 * It primarily displays the header of the login screen.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param insets The padding values representing the insets of the screen, used to adjust layout for system UI.
 * @param state The current state of the login screen, containing data like header titles.
 * @param initialEmail The initial email value for the login form.
 * @param initialPassword The initial password value for the login form.
 * @param onEmailChange The callback to be invoked when the email input changes.
 * @param onPasswordChange The callback to be invoked when the password input changes.
 * @param onLogin The callback to be invoked when the login button is clicked.
 */
@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    insets: PaddingValues,
    state: LoginState?,
    initialEmail: String = "",
    initialPassword: String = "",
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLogin: () -> Unit = {}
) {
    Box {
        Column(modifier = modifier.fillMaxSize()) {
            Header(
                insets = insets,
                title = state?.headerTitleText ?: Res.string.loginHeaderTitle,
                subtitle = state?.headerSubtitleText ?: Res.string.loginHeaderSubtitle
            )
            InputFields(
                emailLabel = state?.emailLabelText ?: Res.string.loginEmailLabel,
                passwordLabel = state?.passwordLabelText ?: Res.string.loginPasswordLabel,
                initialEmail = initialEmail,
                initialPassword = initialPassword,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange
            )
            Spacer(modifier = Modifier.weight(1f))
            CTAButton(
                insets = insets,
                text = state?.loginButtonText ?: Res.string.loginButtonText,
                onClick = onLogin
            )
        }
    }
}

// ? ===============================================================================================
// ? Inner components
// ? ===============================================================================================

/**
 * Composable function for the header section of the login screen.
 *
 * This component displays the main title and subtitle of the login screen.
 * It considers the top insets to ensure content is not obscured by system UI elements.
 *
 * @param insets The padding values representing the insets of the screen.
 * @param title The string resource for the main title of the header.
 * @param subtitle The string resource for the subtitle of the header.
 */
@Composable
private fun Header(
    insets: PaddingValues,
    title: StringResource,
    subtitle: StringResource
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .paddingInsets(
                    insetType = listOf(Insets.Top, Insets.Start, Insets.End),
                    insets = insets,
                    padding = PaddingValues(
                        start = GymDimens.paddingTwenty,
                        top = GymDimens.paddingThirty,
                        end = GymDimens.paddingTwenty
                    )
                ),
            textAlign = TextAlign.Center,
            text = stringResource(resource = title),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Black,
                fontSize = GymDimens.TextSizes.textSizeThirty
            )
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .paddingInsets(
                    insetType = listOf(Insets.Start, Insets.End),
                    padding = PaddingValues(
                        top = GymDimens.paddingFive,
                        start = GymDimens.paddingTwenty,
                        end = GymDimens.paddingTwenty
                    )
                ),
            textAlign = TextAlign.Center,
            text = stringResource(resource = subtitle),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
        )
    }
}

/**
 * Composable function for the input fields section of the login screen.
 *
 * This component displays the email and password input fields.
 * It allows users to input their credentials and handles changes to these inputs.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param emailLabel The string resource for the email input field label.
 * @param passwordLabel The string resource for the password input field label.
 * @param initialEmail The initial value for the email input field.
 * @param initialPassword The initial value for the password input field.
 * @param onEmailChange Callback function invoked when the email input value changes.
 * @param onPasswordChange Callback function invoked when the password input value changes.
 */
@Composable
private fun InputFields(
    modifier: Modifier = Modifier,
    emailLabel: StringResource,
    passwordLabel: StringResource,
    initialEmail: String,
    initialPassword: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .paddingInsets(
                insetType = listOf(Insets.Start, Insets.End),
                padding = PaddingValues(
                    top = GymDimens.paddingThirty,
                    start = GymDimens.paddingThirty,
                    end = GymDimens.paddingThirty
                )
            )
    ) {
        val isPasswordVisible = rememberSaveable { mutableStateOf(false) }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(
                size = GymDimens.paddingTwenty
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
            value = initialEmail,
            onValueChange = onEmailChange,
            placeholder = { Text(text = stringResource(resource = emailLabel)) },
            leadingIcon = {
                Icon(
                    painterResource(Res.drawable.iconEmail),
                    contentDescription = null
                )
            }
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = GymDimens.paddingTen),
            shape = RoundedCornerShape(
                size = GymDimens.paddingTwenty
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
            value = initialPassword,
            onValueChange = onPasswordChange,
            placeholder = { Text(text = stringResource(resource = passwordLabel)) },
            visualTransformation = if (isPasswordVisible.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        isPasswordVisible.value = !isPasswordVisible.value
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = GymDimens.paddingTen),
                        painter = painterResource(
                            resource = if (isPasswordVisible.value) {
                                Res.drawable.iconPasswordShow
                            } else {
                                Res.drawable.iconPasswordHide
                            }
                        ),
                        contentDescription = null
                    )
                }
            },
            leadingIcon = {
                Icon(
                    painterResource(Res.drawable.iconPasswordLock),
                    contentDescription = null
                )
            },
        )
    }
}

/**
 * Composable function for the Call-to-Action (CTA) button.
 *
 * This button is typically used for primary actions like login or sign-up.
 * It applies specific padding based on screen insets to ensure it's well-positioned,
 * especially on devices with gesture navigation or other system UI elements at the bottom.
 *
 * @param modifier The modifier to be applied to the button container.
 * @param insets The padding values representing the insets of the screen, used for bottom padding.
 * @param text The string resource for the text displayed on the button.
 * @param isButtonEnabled Whether the button is enabled or not.
 * @param onClick The callback to be invoked when the button is clicked.
 */
@Composable
private fun CTAButton(
    modifier: Modifier = Modifier,
    insets: PaddingValues,
    text: StringResource,
    onClick: () -> Unit,
    isButtonEnabled: Boolean = true
) {
    Box(
        modifier = modifier
            .paddingInsets(
                insetType = listOf(Insets.Bottom, Insets.Start, Insets.End),
                insets = insets
            )
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = GymDimens.paddingThirty)
                .padding(bottom = GymDimens.paddingTwenty),
            enabled = isButtonEnabled,
            onClick = onClick,
            shape = RoundedCornerShape(size = GymDimens.paddingTwenty)
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = GymDimens.paddingFive),
                text = stringResource(resource = text),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

// ? ===============================================================================================
// ? Previews
// ? ===============================================================================================

@Composable
@Preview
private fun HeaderPreview() {
    Header(
        insets = PaddingValues(),
        title = Res.string.loginHeaderTitle,
        subtitle = Res.string.loginHeaderSubtitle
    )
}