package com.sotsap.apps.gymmanagement.core.framework.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import gymmanagement.composeapp.generated.resources.Lexend_Black
import gymmanagement.composeapp.generated.resources.Lexend_Bold
import gymmanagement.composeapp.generated.resources.Lexend_ExtraBold
import gymmanagement.composeapp.generated.resources.Lexend_ExtraLight
import gymmanagement.composeapp.generated.resources.Lexend_Light
import gymmanagement.composeapp.generated.resources.Lexend_Medium
import gymmanagement.composeapp.generated.resources.Lexend_Regular
import gymmanagement.composeapp.generated.resources.Lexend_SemiBold
import gymmanagement.composeapp.generated.resources.Lexend_Thin
import gymmanagement.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

/**
 * GymTheme is a composable function that applies the MaterialTheme to its content.
 * It sets the typography for the theme using the provideTypography() function.
 *
 * @param content The composable content to be themed.
 */
@Composable
fun GymTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) {
            darkColorScheme()
        } else {
            lightColorScheme()
        },
        typography = provideTypography(),
        content = content
    )
}

/**
 * Provides the typography for the application.
 *
 * This function creates a [Typography] object by copying the default Material Design typography
 * and overriding the `fontFamily` for each text style with the custom font family provided by
 * [provideFontFamily].
 *
 * @return A [Typography] object with the custom font family applied to all text styles.
 */
@Composable
private fun provideTypography() = Typography(
    displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = provideFontFamily()),
    displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = provideFontFamily()),
    displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = provideFontFamily()),
    headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = provideFontFamily()),
    headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = provideFontFamily()),
    headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = provideFontFamily()),
    titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = provideFontFamily()),
    titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = provideFontFamily()),
    titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = provideFontFamily()),
    bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = provideFontFamily()),
    bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = provideFontFamily()),
    bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = provideFontFamily()),
    labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = provideFontFamily()),
    labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = provideFontFamily()),
    labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = provideFontFamily())
)

/**
 * Provides the custom font family for the application.
 *
 * This function defines and returns a [FontFamily] consisting of various weights
 * of the "Lexend" font, loaded from the application's resources.
 *
 * @return A [FontFamily] object configured with the Lexend font.
 */
@Composable
private fun provideFontFamily() = FontFamily(
    Font(Res.font.Lexend_Thin, weight = FontWeight.Thin),
    Font(Res.font.Lexend_ExtraLight, weight = FontWeight.ExtraLight),
    Font(Res.font.Lexend_Light, weight = FontWeight.Light),
    Font(Res.font.Lexend_Regular, weight = FontWeight.Normal),
    Font(Res.font.Lexend_Medium, weight = FontWeight.Medium),
    Font(Res.font.Lexend_SemiBold, weight = FontWeight.SemiBold),
    Font(Res.font.Lexend_Bold, weight = FontWeight.Bold),
    Font(Res.font.Lexend_ExtraBold, weight = FontWeight.ExtraBold),
    Font(Res.font.Lexend_Black, weight = FontWeight.Black)
)