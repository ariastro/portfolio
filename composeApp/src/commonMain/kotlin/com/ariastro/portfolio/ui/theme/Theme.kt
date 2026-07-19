package com.ariastro.portfolio.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtraColors(
    val panel: Color,
    val muted: Color,
    val faint: Color,
    val codeBg: Color,
    val accent: Color,
    val accentSoft: Color,
    val isDark: Boolean,
)

val LocalExtra = staticCompositionLocalOf {
    ExtraColors(LPanel, LMuted, LFaint, LCode, Terminal, Terminal.copy(0.12f), false)
}

object PortfolioTheme {
    val extra: ExtraColors
        @Composable get() = LocalExtra.current
}

@Composable
fun PortfolioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val scheme = if (darkTheme) {
        darkColorScheme(
            primary = Terminal,
            onPrimary = Color.Black,
            background = DBg,
            onBackground = DInk,
            surface = DPanel,
            onSurface = DInk,
            surfaceVariant = DCode,
            onSurfaceVariant = DMuted,
            outline = DLine,
        )
    } else {
        lightColorScheme(
            primary = TerminalDim,
            onPrimary = Color.White,
            background = LBg,
            onBackground = LInk,
            surface = LPanel,
            onSurface = LInk,
            surfaceVariant = LCode,
            onSurfaceVariant = LMuted,
            outline = LLine,
        )
    }

    val extra = if (darkTheme) {
        ExtraColors(DPanel, DMuted, DFaint, DCode, Terminal, Terminal.copy(0.14f), true)
    } else {
        ExtraColors(LPanel, LMuted, LFaint, LCode, TerminalDim, Terminal.copy(0.14f), false)
    }

    CompositionLocalProvider(LocalExtra provides extra) {
        MaterialTheme(
            colorScheme = scheme,
            typography = portfolioTypography(),
            content = content,
        )
    }
}
