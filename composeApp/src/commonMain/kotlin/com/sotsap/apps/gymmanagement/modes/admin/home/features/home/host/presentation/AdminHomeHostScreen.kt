package com.sotsap.apps.gymmanagement.modes.admin.home.features.home.host.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sotsap.apps.gymmanagement.core.compose.BaseScreen
import com.sotsap.apps.gymmanagement.core.framework.components.navigation.BottomNavigationItem
import com.sotsap.apps.gymmanagement.core.models.Route
import com.sotsap.apps.gymmanagement.modes.admin.home.features.home.dashboard.presentation.DashboardAdminScreen
import com.sotsap.apps.gymmanagement.modes.admin.home.features.home.messages.presentation.MessagesAdminScreen
import com.sotsap.apps.gymmanagement.modes.admin.home.features.home.profile.presentation.ProfileAdminScreen
import com.sotsap.apps.gymmanagement.modes.admin.home.features.home.receipts.presentation.ReceiptAdminScreen
import com.sotsap.apps.gymmanagement.modes.admin.home.features.home.schedule.presentation.ScheduleAdminScreen
import com.sotsap.apps.gymmanagement.modes.admin.home.navigation.AdminHomeNavigation
import com.sotsap.apps.gymmanagement.modes.admin.home.navigation.adminHomeNavigationItems
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Represents the main home screen for the admin user.
 * This is the entry point for various admin functionalities.
 */
@Serializable object AdminHomeScene: Route()

@Composable
fun AdminHomeHostScreen() = BaseScreen<AdminHomeHostState, AdminHomeHostEvents, AdminHomeHostViewModel> { state, event, viewModel ->

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            Column {
                HorizontalDivider(
                    thickness = 2.dp
                )
                NavigationBarProvider(
                    navController = navController,
                    onItemClick = { viewModel.onNavigationItemSelected(item = it.route) }
                )
            }
        },
        content = {
            NavHostProvider(
                navController = navController,
                paddingValues = it
            )
        }
    )
}

/**
 * Provides the navigation bar for the admin home screen.
 *
 * This composable function creates a [NavigationBar] with items defined in [adminHomeNavigationItems].
 * It highlights the currently selected item based on the [navController]'s current route.
 * Icons for navigation items are animated using [AnimatedContent] to provide a smooth transition
 * between selected and unselected states.
 *
 * @param navController The [NavHostController] used to determine the current navigation route.
 * @param onItemClick An optional callback function to handle item clicks.
 */
@Suppress("D")
@Composable
private fun NavigationBarProvider(
    navController: NavHostController,
    onItemClick: (BottomNavigationItem<AdminHomeNavigation>) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        val currentRoute = navController
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route
        adminHomeNavigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = Modifier
                    .padding(
                        start = if (index == 0) 4.dp else 0.dp,
                        end = if (index == adminHomeNavigationItems.lastIndex) 4.dp else 0.dp
                    ),
                selected = currentRoute == item.route.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = MaterialTheme.colorScheme.outline.copy(alpha = .8f),
                    unselectedTextColor = MaterialTheme.colorScheme.outline.copy(alpha = .8f),
                ),
                onClick = {
                    onItemClick(item)
                    navController.navigate(item.route.route) {
                        popUpTo(AdminHomeNavigation.Dashboard.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = {
                    Text(text = stringResource(item.label))
                },
                icon = {
                    NavigationBarIconProvider(
                        item = item,
                        isSelected = currentRoute == item.route.route
                    )
                }
            )
        }
    }
}

// ? ===============================================================================================
// ? Navigation bar breakdown
// ? ===============================================================================================

/**
 * Provides an animated icon for a navigation bar item.
 *
 * This composable function displays an icon that animates its scale, fade, and tint
 * based on whether it is selected or not. It uses [AnimatedContent] for the scale and fade
 * animations and [animateColorAsState] for the tint animation, both with spring physics
 * for a bouncy effect.
 *
 * @param item The [BottomNavigationItem] containing the icon resources and label.
 * @param isSelected A boolean indicating whether the item is currently selected.
 */
@Composable
private fun NavigationBarIconProvider(
    item: BottomNavigationItem<AdminHomeNavigation>,
    isSelected: Boolean
) {
    AnimatedContent(
        modifier = Modifier.size(24.dp),
        contentAlignment = Alignment.Center,
        targetState = isSelected,
        label = "bottomNavigationIconAnimation",
        transitionSpec = {
            (scaleIn(
                initialScale = 0.8f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ) + fadeIn()) togetherWith
                    (scaleOut(
                        targetScale = 0.8f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ) + fadeOut())
        },
    ) { isSelected ->
        // Animate color with a bouncy spring
        val animatedTint by animateColorAsState(
            targetValue = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.outline.copy(alpha = .8f),
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
            label = "iconTint"
        )
        Icon(
            painter = painterResource(
                resource = if (isSelected) {
                    item.icon.stateActive
                } else {
                    item.icon.stateIdle
                }
            ),
            tint = animatedTint, // 👈 smooth bouncy color transition
            contentDescription = stringResource(resource = item.label),
            modifier = Modifier.fillMaxSize() // fit neatly in 24.dp
        )
    }
}


// ? ===============================================================================================
// ? Navigation host
// ? ===============================================================================================

/**
 * Provides the navigation host for the admin home screen.
 * This composable function is responsible for setting up the navigation graph for the admin home screen.
 * It uses a [NavHost] to define the different screens that can be navigated to within the admin home screen.
 *
 * @param navController The [NavHostController] that manages the navigation within this host.
 *                      Defaults to a new [NavHostController] remembered by the composable.
 * @param paddingValues The [PaddingValues] to apply to the [NavHost] container.
 *                      This is typically used to accommodate system UI elements like status bars or navigation bars.
 */
@Composable
private fun NavHostProvider(
    navController: NavHostController = rememberNavController(),
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = AdminHomeNavigation.Dashboard.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = AdminHomeNavigation.Dashboard.route) { DashboardAdminScreen() }
        composable(route = AdminHomeNavigation.Receipts.route) { ReceiptAdminScreen() }
        composable(route = AdminHomeNavigation.Messages.route) { MessagesAdminScreen() }
        composable(route = AdminHomeNavigation.Schedule.route) { ScheduleAdminScreen() }
        composable(route = AdminHomeNavigation.Profile.route) { ProfileAdminScreen() }
    }
}