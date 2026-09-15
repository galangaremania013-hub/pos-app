package com.workid.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = EmeraldGreen,
    onPrimary = White,
    primaryContainer = EmeraldGreenDark,
    onPrimaryContainer = EmeraldGreenLight,
    secondary = MutedGrey,
    onSecondary = White,
    tertiary = Info,
    onTertiary = White,
    error = Error,
    onError = White,
    background = DarkSlate,
    onBackground = White,
    surface = CardSurface,
    onSurface = White,
    surfaceVariant = CardSurfaceLight,
    onSurfaceVariant = MutedGrey
)

private val LightColorScheme = lightColorScheme(
    primary = EmeraldGreen,
    onPrimary = White,
    primaryContainer = EmeraldGreenLight,
    onPrimaryContainer = EmeraldGreenDark,
    secondary = MutedGreyDark,
    onSecondary = White,
    tertiary = Info,
    onTertiary = White,
    error = Error,
    onError = White,
    background = Color(0xFFF1F5F9),
    onBackground = DarkSlate,
    surface = Color(0xFFFFFFFF),
    onSurface = DarkSlate,
    surfaceVariant = Color(0xFFE2E8F0),
    onSurfaceVariant = MutedGreyDark
)

@Composable
fun WorkIDTheme(
    darkTheme: Boolean = true, // Default to dark theme as per myBCA style
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            // Dynamic color is not typically used in branded apps
            DarkColorScheme
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DarkSlate.toArgb()
            window.navigationBarColor = DarkSlate.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
