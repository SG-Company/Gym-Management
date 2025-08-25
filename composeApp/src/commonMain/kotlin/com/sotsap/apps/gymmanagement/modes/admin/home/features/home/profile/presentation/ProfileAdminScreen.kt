package com.sotsap.apps.gymmanagement.modes.admin.home.features.home.profile.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sotsap.apps.gymmanagement.core.compose.BaseScreen
import io.github.jan.supabase.realtime.Column

/**
 * Composable function for the Profile Admin screen.
 * This screen displays the profile information for an admin user and provides an option to logout.
 *
 * @param onLogout A lambda function to be invoked when the logout button is clicked.
 */
@Composable
fun ProfileAdminScreen(
    onLogout: () -> Unit
) = BaseScreen<ProfileAdminState, ProfileAdminEvents, ProfileAdminViewModel> { state, event, viewModel ->

    ProfileAdminContent(
        state = state,
        onLogout = onLogout
    )
}

@Composable
private fun ProfileAdminContent(
    state: ProfileAdminState?,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ProfileAdminToolbar(
            title = state?.profile?.name ?: "",
            email = state?.profile?.email ?: ""
        )
    }
}

@Composable
private fun ProfileAdminToolbar(title: String, email: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            modifier = Modifier
                .padding(top = 5.dp),
            text = email,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}