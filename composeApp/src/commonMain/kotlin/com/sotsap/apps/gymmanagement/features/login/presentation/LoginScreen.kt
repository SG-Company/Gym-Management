package com.sotsap.apps.gymmanagement.features.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sotsap.apps.gymmanagement.core.compose.BaseScreen
import com.sotsap.apps.gymmanagement.core.navigation.handleOnBack
import com.sotsap.apps.gymmanagement.di.initKoin
import com.sotsap.apps.gymmanagement.framework.paddingNormal
import gymmanagement.composeapp.generated.resources.Res
import gymmanagement.composeapp.generated.resources.icon_close
import org.jetbrains.compose.resources.painterResource
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
) = BaseScreen<LoginState, LoginIntents, LoginViewModel> { state, intent, viewModel ->

    if (state == null) return@BaseScreen

    val intent = { intent: LoginIntents ->
        when (intent) {
            LoginIntents.OnLogin -> onLogin
            LoginIntents.OnRegister -> onRegister
            LoginIntents.Close -> { navController.popBackStack() }
        }
    }

    handleOnBack { intent(LoginIntents.Close) }

    LoginContent(
        insets = insets,
        modifier = modifier,
        state = state,
        viewModel = viewModel,
        onLogin = { intent(LoginIntents.OnLogin) },
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                modifier = Modifier
                    .padding(top = paddingNormal),
                onClick = onClose,
                colors = IconButtonDefaults.filledIconButtonColors()
            ) {
                Icon(
                    painter = painterResource(resource = Res.drawable.icon_close),
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = paddingNormal)
                    .padding(horizontal = paddingNormal),
                text = stringResource(resource = state.wordings.header),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                modifier = Modifier.padding(horizontal = paddingNormal),
                text = stringResource(resource = state.wordings.subtitle),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }
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
        onLogin = {}
    )
}







