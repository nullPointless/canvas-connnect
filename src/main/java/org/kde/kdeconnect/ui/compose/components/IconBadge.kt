/*
 * SPDX-FileCopyrightText: 2026 nullPointless
 *
 * SPDX-License-Identifier: GPL-2.0-only OR GPL-3.0-only OR LicenseRef-KDE-Accepted-GPL
 */

package org.kde.kdeconnect.ui.compose.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.kde.kdeconnect.ui.compose.KdeTheme
import org.kde.kdeconnect.ui.compose.darken
import org.kde.kdeconnect.ui.compose.lighten

/**
 * A tinted, rounded-square icon container in the style of iOS Settings:
 * a solid accent color with a subtle top-lit vertical gradient and a white glyph.
 */
@Composable
fun IconBadge(
    icon: Painter,
    tint: Color,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    cornerPercent: Int = 30,
    iconScale: Float = 0.55f,
    contentDescription: String? = null,
) {
    val shape = RoundedCornerShape(percent = cornerPercent)
    Box(
        modifier = modifier
            .size(size)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(tint.lighten(0.12f), tint.darken(0.10f)),
                ),
                shape = shape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = size * (1f - iconScale) / 2f, vertical = size * (1f - iconScale) / 2f),
        )
    }
}

/** [IconBadge] variant that loads a drawable resource. */
@Composable
fun IconBadge(
    @DrawableRes iconRes: Int,
    tint: Color,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    cornerPercent: Int = 30,
    iconScale: Float = 0.55f,
    contentDescription: String? = null,
) {
    IconBadge(
        icon = painterResource(id = iconRes),
        tint = tint,
        modifier = modifier,
        size = size,
        cornerPercent = cornerPercent,
        iconScale = iconScale,
        contentDescription = contentDescription,
    )
}

/** [IconBadge] variant for material image vectors. */
@Composable
fun IconBadge(
    imageVector: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    cornerPercent: Int = 30,
    iconScale: Float = 0.55f,
    contentDescription: String? = null,
) {
    IconBadge(
        icon = rememberVectorPainter(imageVector),
        tint = tint,
        modifier = modifier,
        size = size,
        cornerPercent = cornerPercent,
        iconScale = iconScale,
        contentDescription = contentDescription,
    )
}

@KdePortraitThemePreviews
@Composable
private fun IconBadgePreview() {
    KdeTheme(context = LocalContext.current) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        ) {
            IconBadge(iconRes = org.kde.kdeconnect_tp.R.drawable.ic_device_phone_32dp, tint = Color(0xFF34C759))
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(8.dp))
            IconBadge(iconRes = org.kde.kdeconnect_tp.R.drawable.ic_device_laptop_32dp, tint = Color(0xFF0A84FF))
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(8.dp))
            IconBadge(iconRes = org.kde.kdeconnect_tp.R.drawable.ic_device_tv_32dp, tint = Color(0xFFFF9500), size = 48.dp)
        }
    }
}
