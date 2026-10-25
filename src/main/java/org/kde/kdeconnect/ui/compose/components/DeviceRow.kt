/*
 * SPDX-FileCopyrightText: 2026 nullPointless
 *
 * SPDX-License-Identifier: GPL-2.0-only OR GPL-3.0-only OR LicenseRef-KDE-Accepted-GPL
 */

package org.kde.kdeconnect.ui.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.kde.kdeconnect.ui.compose.KdeTheme
import org.kde.kdeconnect.ui.compose.deviceAccent
import org.kde.kdeconnect.ui.compose.model.device.DeviceUiModel

/**
 * A device row in the style of an iOS grouped list entry:
 * tinted squircle badge, name, colored status line and a light chevron.
 */
@Composable
fun DeviceRow(
    device: DeviceUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    statusText: String? = null,
    statusColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 64.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (device.icon > 0) {
            IconBadge(
                iconRes = device.icon,
                tint = deviceAccent(device.icon),
                size = 42.dp,
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = device.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (statusText != null) {
                Text(
                    text = statusText,
                    style = MaterialTheme.typography.bodySmall,
                    color = statusColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp),
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.45f),
        )
    }
}

@KdePortraitThemePreviews
@Composable
private fun DeviceRowPreview() {
    KdeTheme(context = LocalContext.current) {
        ListGroup {
            DeviceRow(
                device = DeviceUiModel(
                    id = "1",
                    icon = org.kde.kdeconnect_tp.R.drawable.ic_device_laptop_32dp,
                    name = "MacBook Pro",
                    summaryRes = 0,
                    isReachable = true,
                    isPaired = true,
                ),
                statusText = "Connected",
                statusColor = Color(0xFF248A3D),
                onClick = { /* Do nothing */ },
            )
            ListGroupDivider()
            DeviceRow(
                device = DeviceUiModel(
                    id = "2",
                    icon = org.kde.kdeconnect_tp.R.drawable.ic_device_desktop_32dp,
                    name = "Studio PC",
                    summaryRes = 0,
                    isReachable = true,
                    isPaired = false,
                ),
                statusText = "Tap to pair",
                statusColor = Color(0xFF007AFF),
                onClick = { /* Do nothing */ },
            )
        }
    }
}
