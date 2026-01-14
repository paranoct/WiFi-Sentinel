package com.wifisentinel.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun WiFiSentinelTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = BluePrimaryDark,
            secondary = BlueSecondaryDark,
            tertiary = BlueTertiaryDark
        )
    } else {
        lightColorScheme(
            primary = BluePrimary,
            secondary = BlueSecondary,
            tertiary = BlueTertiary
        )
    }

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}
