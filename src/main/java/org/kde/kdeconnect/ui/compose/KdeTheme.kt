/*
 * SPDX-FileCopyrightText: 2024 Albert Vaca Cintora <albertvaka@gmail.com>
 * SPDX-FileCopyrightText: 2026 nullPointless
 *
 * SPDX-License-Identifier: GPL-2.0-only OR GPL-3.0-only OR LicenseRef-KDE-Accepted-GPL
 */

package org.kde.kdeconnect.ui.compose

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * Canvas theme: a consistent, crafted brand identity across the app
 * (light: white cards on mist gray; dark: graphite on true black),
 * with an SF-inspired type ramp and generous corner radii.
 */
@Composable
fun KdeTheme(context: Context, content: @Composable () -> Unit) {
    @Suppress("UNUSED_VARIABLE") val themeContext = context // kept for API compatibility
    val colorScheme = if (isSystemInDarkTheme()) CanvasDarkColors else CanvasLightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CanvasTypography,
        shapes = CanvasShapes,
        content = content,
    )
}
