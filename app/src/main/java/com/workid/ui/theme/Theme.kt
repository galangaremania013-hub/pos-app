package com.workid.ui.theme

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
    
    secondary = EmeraldGreenLight,
    onSecondary = DarkSlate,
    secondaryContainer = DarkSlateLight,
    onSecondaryContainer = MutedGrey,
    
    tertiary = InfoBlue,
    onTertiary = White,
    tertiaryContainer = DarkSlate,
    onTertiaryContainer = InfoBlue,
    
    background = DarkSlate,
    onBackground = White,
    
    surface = DarkSlateLight,
    onSurface = White,
    surfaceVariant = DarkSlateLight,
    onSurfaceVariant = MutedGrey,
    
    error = ErrorRed,
    onError = White,
    errorContainer = ErrorRed.copy(alpha = 0.3f),
    onErrorContainer = ErrorRed,
    
    outline = TextSecondary
)

private val LightColorScheme = lightColorScheme(
    primary = EmeraldGreen,
    onPrimary = White,
    primaryContainer = EmeraldGreenLight,
    onPrimaryContainer = EmeraldGreenDark,
    
    secondary = EmeraldGreenDark,
    onSecondary = White,
    secondaryContainer = EmeraldGreenLight,
    onSecondaryContainer = EmeraldGreenDark,
    
    tertiary = InfoBlue,
    onTertiary = White,
    tertiaryContainer = InfoBlue.copy(alpha = 0.1f),
    onTertiaryContainer = InfoBlue,
    
    background = Color(0xFFF1F5F9),
    onBackground = DarkSlate,
    
    surface = Color(0xFFFFFFFF),
    onSurface = DarkSlate,
    surfaceVariant = Color(0xFFE2E8F0),
    onSurfaceVariant = TextSecondary,
    
    error = ErrorRed,
    onError = White,
    errorContainer = ErrorRed.copy(alpha = 0.1f),
    onErrorContainer = ErrorRed,
    
    outline = TextSecondary
)

@Composable
fun WorkIDTheme(
    darkTheme: Boolean = true, // Default to dark theme as per myBCA style
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
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
