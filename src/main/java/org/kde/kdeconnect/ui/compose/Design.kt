/*
 * SPDX-FileCopyrightText: 2026 nullPointless
 *
 * SPDX-License-Identifier: GPL-2.0-only OR GPL-3.0-only OR LicenseRef-KDE-Accepted-GPL
 */

package org.kde.kdeconnect.ui.compose

import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.kde.kdeconnect_tp.R

/**
 * Canvas Design tokens.
 *
 * A calm, Apple-inspired visual language: a single confident accent blue,
 * neutral grouped surfaces (white cards on mist-gray in light mode,
 * elevated graphite on true black in dark mode), SF-inspired type ramp,
 * and generous 20dp corner radii.
 */

// ---------------------------------------------------------------------------
// Accent palette (used for icon badges and status colors)
// ---------------------------------------------------------------------------

object CanvasAccents {
    val blue = Color(0xFF0A84FF)
    val green = Color(0xFF34C759)
    val indigo = Color(0xFF5856D6)
    val orange = Color(0xFFFF9500)
    val pink = Color(0xFFFF2D55)
    val purple = Color(0xFFAF52DE)
    val teal = Color(0xFF00C7BE)
    val red = Color(0xFFFF3B30)
    val gray = Color(0xFF8E8E93)
}

/** Color a badge/icon should use for a given device-type icon resource. */
fun deviceAccent(iconRes: Int): Color = when (iconRes) {
    R.drawable.ic_device_phone_32dp -> CanvasAccents.green
    R.drawable.ic_device_tablet_32dp -> CanvasAccents.purple
    R.drawable.ic_device_laptop_32dp -> CanvasAccents.blue
    R.drawable.ic_device_desktop_32dp -> CanvasAccents.indigo
    R.drawable.ic_device_tv_32dp -> CanvasAccents.orange
    else -> CanvasAccents.gray
}

/** Color a badge/icon should use for a given plugin action icon resource. */
fun pluginAccent(iconRes: Int): Color = when (iconRes) {
    R.drawable.mpris_plugin_action_24dp -> CanvasAccents.pink
    R.drawable.touchpad_plugin_action_24dp -> CanvasAccents.blue
    R.drawable.tv_remote_24px -> CanvasAccents.teal
    R.drawable.run_command_plugin_icon_24dp -> CanvasAccents.gray
    R.drawable.share_plugin_action_24dp -> CanvasAccents.green
    R.drawable.ic_presenter_24dp -> CanvasAccents.indigo
    R.drawable.ic_draw_24dp -> CanvasAccents.purple
    R.drawable.ic_baseline_content_paste_24 -> CanvasAccents.teal
    else -> CanvasAccents.blue
}

// ---------------------------------------------------------------------------
// Status colors
// ---------------------------------------------------------------------------

@Immutable
data class CanvasStatusColors(
    val connected: Color,
    val warning: Color,
    val error: Color,
    val neutral: Color,
)

val CanvasStatusLight = CanvasStatusColors(
    connected = Color(0xFF248A3D),
    warning = Color(0xFFB25000),
    error = Color(0xFFD32F2F),
    neutral = Color(0xFF55606E),
)

val CanvasStatusDark = CanvasStatusColors(
    connected = Color(0xFF30D158),
    warning = Color(0xFFFFB340),
    error = Color(0xFFFF6B60),
    neutral = Color(0xFFA6ADB8),
)

// ---------------------------------------------------------------------------
// Color schemes
// ---------------------------------------------------------------------------

val CanvasLightColors = lightColorScheme(
    primary = Color(0xFF007AFF),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD6EAFF),
    onPrimaryContainer = Color(0xFF002B52),
    inversePrimary = Color(0xFF9BCBFF),
    secondary = Color(0xFF586573),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFE3EAF3),
    onSecondaryContainer = Color(0xFF161F28),
    tertiary = Color(0xFFD26A00),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFE4C7),
    onTertiaryContainer = Color(0xFF3B2100),
    error = Color(0xFFD32F2F),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFDE0DD),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFF2F2F7),
    onBackground = Color(0xFF1B1B1D),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1B1B1D),
    surfaceVariant = Color(0xFFE9E9EE),
    onSurfaceVariant = Color(0xFF55606E),
    surfaceTint = Color(0xFF007AFF),
    inverseSurface = Color(0xFF2E3033),
    inverseOnSurface = Color(0xFFF2F2F7),
    outline = Color(0xFFAAB2BC),
    outlineVariant = Color(0xFFE3E5EA),
    scrim = Color(0xFF000000),
    surfaceDim = Color(0xFFDCDCE1),
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFFFFFF),
    surfaceContainer = Color(0xFFF7F7FA),
    surfaceContainerHigh = Color(0xFFF0F0F4),
    surfaceContainerHighest = Color(0xFFEAEAEF),
)

val CanvasDarkColors = darkColorScheme(
    primary = Color(0xFF0A84FF),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF0F4E94),
    onPrimaryContainer = Color(0xFFCCE4FF),
    inversePrimary = Color(0xFF0061BE),
    secondary = Color(0xFFB3BECB),
    onSecondary = Color(0xFF1B2530),
    secondaryContainer = Color(0xFF313D4A),
    onSecondaryContainer = Color(0xFFDAE3EE),
    tertiary = Color(0xFFFFA24A),
    onTertiary = Color(0xFF3B2600),
    tertiaryContainer = Color(0xFF5B3B00),
    onTertiaryContainer = Color(0xFFFFDCBB),
    error = Color(0xFFFF6B60),
    onError = Color(0xFF3A0001),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD5),
    background = Color(0xFF000000),
    onBackground = Color(0xFFF2F2F7),
    surface = Color(0xFF1C1C1E),
    onSurface = Color(0xFFF2F2F7),
    surfaceVariant = Color(0xFF2C2C30),
    onSurfaceVariant = Color(0xFFA6ADB8),
    surfaceTint = Color(0xFF0A84FF),
    inverseSurface = Color(0xFFF2F2F7),
    inverseOnSurface = Color(0xFF2E3033),
    outline = Color(0xFF494F57),
    outlineVariant = Color(0xFF303035),
    scrim = Color(0xFF000000),
    surfaceDim = Color(0xFF101012),
    surfaceBright = Color(0xFF36363A),
    surfaceContainerLowest = Color(0xFF000000),
    surfaceContainerLow = Color(0xFF1C1C1E),
    surfaceContainer = Color(0xFF232327),
    surfaceContainerHigh = Color(0xFF2A2A2F),
    surfaceContainerHighest = Color(0xFF313136),
)

// ---------------------------------------------------------------------------
// Typography — SF-inspired ramp on the platform sans (Roboto)
// ---------------------------------------------------------------------------

val CanvasTypography = Typography(
    displaySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 41.sp,
        letterSpacing = (-0.4).sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 37.sp,
        letterSpacing = (-0.4).sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.4).sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        letterSpacing = (-0.3).sp,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.3).sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.2).sp,
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.2).sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.1.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.3.sp,
    ),
)

// ---------------------------------------------------------------------------
// Shapes — continuous, generous radii
// ---------------------------------------------------------------------------

val CanvasShapes = Shapes(
    extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(10.dp),
    small = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
    medium = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
    large = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
    extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(28.dp),
)

// ---------------------------------------------------------------------------
// Color utilities
// ---------------------------------------------------------------------------

/** Blend this color towards white by [fraction] (0..1). */
fun Color.lighten(fraction: Float): Color = lerp(this, Color.White, fraction.coerceIn(0f, 1f))

/** Blend this color towards black by [fraction] (0..1). */
fun Color.darken(fraction: Float): Color = lerp(this, Color.Black, fraction.coerceIn(0f, 1f))
