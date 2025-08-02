package com.sotsap.apps.gymmanagement.features.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sotsap.apps.gymmanagement.core.compose.BaseScreen
import com.sotsap.apps.gymmanagement.core.navigation.handleOnBack
import com.sotsap.apps.gymmanagement.di.initKoin
import com.sotsap.apps.gymmanagement.framework.paddingNormal
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Composable function for the Login Screen.
 *
 * This screen utilizes the `BaseScreen` composable to manage its state, intents, and view model.
 * It is responsible for displaying the UI elements for user login and handling user interactions.
 *
 * @param insets The [PaddingValues] representing the insets for the screen.
 * @param modifier Optional [Modifier] for this composable.
 * @param navController The [NavHostController] used for navigation.
 * @param onRegister Callback invoked when the user clicks the register button.
 * @param onLogin Callback invoked when the user clicks the login button.
 */
@Composable
fun LoginScreen(
    insets: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onRegister: () -> Unit,
    onLogin: () -> Unit,
    onClose: () -> Unit
) = BaseScreen<LoginState, LoginIntents, LoginViewModel> { state, intent, viewModel ->

    if (state == null) return@BaseScreen

    val intent = { intent: LoginIntents ->
        when (intent) {
            LoginIntents.OnLogin -> { onLogin() }
            LoginIntents.OnRegister -> { onRegister() }
            LoginIntents.Close -> { onClose() }
        }
    }

    handleOnBack { intent(LoginIntents.Close) }

    LoginContent(
        insets = insets,
        modifier = modifier,
        state = state,
        viewModel = viewModel,
        onLogin = { viewModel.onLogin() },
        onRegister = { intent(LoginIntents.OnRegister) },
        onClose = { intent(LoginIntents.Close) }
    )
}

@Composable
private fun LoginContent(
    insets: PaddingValues,
    modifier: Modifier = Modifier,
    state: LoginState,
    viewModel: LoginViewModel,
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onClose: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .consumeWindowInsets(insets)
            .padding(paddingNormal), // Added overall padding
        horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally
    ) {
        IconButton(
            modifier = Modifier
                .align(Alignment.Start), // Align close button to the start
            onClick = onClose,
            colors = IconButtonDefaults.filledIconButtonColors()
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.height(paddingNormal)) // Added spacer

        Text(
            modifier = Modifier
                .padding(horizontal = paddingNormal),
            text = stringResource(resource = state.wordings.header),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(paddingNormal)) // Added spacer

        Text(
            modifier = Modifier.padding(horizontal = paddingNormal),
            text = stringResource(resource = state.wordings.subtitle),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(paddingNormal * 2)) // Added more spacer

        LoginInputFields(
            username = state.username,
            password = state.password,
            onUsernameChange = { viewModel.onUsernameChange(it) },
            onPasswordChange = { viewModel.onPasswordChange(it) }
        )

        Spacer(modifier = Modifier.weight(1f)) // Pushes buttons to the bottom

        Button(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(state.wordings.loginButton)) // Added login button text
        }
        Spacer(modifier = Modifier.height(paddingNormal))
        Button(
            onClick = onRegister,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("register") // Added register button text
        }
    }
}

@Composable
private fun LoginInputFields(
    username: String,
    password: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(), // Ensure column takes full width
        horizontalAlignment = Alignment.CenterHorizontally, // Center fields
        verticalArrangement = Arrangement.spacedBy(paddingNormal) // Add space between fields
    ) {
        TextField(
            value = username,
            onValueChange = { onUsernameChange(it) },
            modifier = Modifier.fillMaxWidth(), // Make TextField take full width
            label = { Text("Username") } // Added label
        )
        TextField(
            value = password,
            onValueChange = { onPasswordChange(it) },
            modifier = Modifier.fillMaxWidth(), // Make TextField take full width
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Password") } // Added label
        )
    }
}

@Composable
@Preview
private fun LoginPreview() {
    val navController = rememberNavController()
    initKoin {}
    LoginScreen(
        insets = PaddingValues(),
        navController = navController,
        onRegister = {},
        onLogin = {},
        onClose = {}
    )
}
