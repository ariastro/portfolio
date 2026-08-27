package com.ariastro.portfolio.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ariastro.portfolio.resources.JetBrainsMono_Medium
import com.ariastro.portfolio.resources.JetBrainsMono_Regular
import com.ariastro.portfolio.resources.Res
import com.ariastro.portfolio.resources.SpaceGrotesk_Bold
import com.ariastro.portfolio.resources.SpaceGrotesk_Medium
import com.ariastro.portfolio.resources.SpaceGrotesk_Regular
import org.jetbrains.compose.resources.Font

@Composable
fun displayFamily(): FontFamily = FontFamily(
    Font(Res.font.SpaceGrotesk_Regular, FontWeight.Normal),
    Font(Res.font.SpaceGrotesk_Medium, FontWeight.Medium),
    Font(Res.font.SpaceGrotesk_Bold, FontWeight.Bold),
    Font(Res.font.SpaceGrotesk_Bold, FontWeight.SemiBold),
)

/**
 * Mono family. Deliberately two files, not three: the bold weight was 271KB of transfer for
 * two on-screen uses (Kotlin keywords and the Run button), so Medium now serves Bold and
 * SemiBold as well.
 */
@Composable
fun monoFamily(): FontFamily = FontFamily(
    Font(Res.font.JetBrainsMono_Regular, FontWeight.Normal),
    Font(Res.font.JetBrainsMono_Medium, FontWeight.Medium),
    Font(Res.font.JetBrainsMono_Medium, FontWeight.SemiBold),
    Font(Res.font.JetBrainsMono_Medium, FontWeight.Bold),
)

@Composable
fun portfolioTypography(): Typography {
    val display = displayFamily()
    val mono = monoFamily()

    return Typography(
        displayLarge = TextStyle(
            fontFamily = display,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
            lineHeight = 54.sp,
            letterSpacing = (-1).sp,
        ),
        displayMedium = TextStyle(
            fontFamily = display,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = (-0.5).sp,
        ),
        headlineLarge = TextStyle(
            fontFamily = display,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = display,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 28.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = display,
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            lineHeight = 24.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = display,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = display,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 26.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = display,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 22.sp,
        ),
        labelLarge = TextStyle(
            fontFamily = mono,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = mono,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = mono,
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            lineHeight = 16.sp,
        ),
    )
}
