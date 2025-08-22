package com.sotsap.apps.gymmanagement.features.login.presentation

import org.jetbrains.compose.resources.StringResource

data class LoginState(
    val progress: Boolean,
    val headerTitleText: StringResource,
    val headerSubtitleText: StringResource,
    val emailLabelText: StringResource,
    val passwordLabelText: StringResource,
    val loginButtonText: StringResource,
    val loginButtonEnabled: Boolean = false,
    val emailErrorText: StringResource?,
    val passwordErrorText: StringResource?
)
