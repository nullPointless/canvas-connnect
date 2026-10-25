/*
 * SPDX-FileCopyrightText: 2026 Saul Cintero Chocarro <scintero@gmail.com>
 * SPDX-FileCopyrightText: 2026 nullPointless
 *
 * SPDX-License-Identifier: GPL-2.0-only OR GPL-3.0-only OR LicenseRef-KDE-Accepted-GPL
 */

package org.kde.kdeconnect.ui.compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.kde.kdeconnect.ui.compose.KdeTheme

private val CardShape = RoundedCornerShape(20.dp)
private val CardBorder: BorderStroke
    @Composable get() = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
    )

/**
 * A flat, soft-edged card: generous 20dp corners, hairline border,
 * no elevation — grouped-surface aesthetic instead of shadows.
 */
@Composable
fun KdeCard(
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit),
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = CardShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = CardBorder,
        content = content
    )
}

/**
 * An inset grouped list container (iOS Settings style): one continuous
 * rounded surface that hosts multiple rows separated by inset dividers.
 */
@Composable
fun ListGroup(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                shape = CardShape,
            )
            .border(CardBorder, CardShape),
    ) {
        content()
    }
}

/** Hairline divider used inside a [ListGroup], inset to align with row text. */
@Composable
fun ListGroupDivider(
    inset: Dp = 76.dp,
) {
    HorizontalDivider(
        modifier = Modifier.padding(start = inset),
        thickness = 0.75.dp,
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
    )
}

@KdePortraitThemePreviews
@Composable
private fun KdeCardPreview() {
    KdeTheme(context = LocalContext.current) {
        KdeCard(
            modifier = Modifier.fillMaxWidth(),
            content = {
                Text(
                    text = "A very long device name that might wrap into multiple lines",
                    modifier = Modifier.padding(all = 16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            onClick = { /* Do nothing */ }
        )
    }
}

@KdePortraitThemePreviews
@Composable
private fun ListGroupPreview() {
    KdeTheme(context = LocalContext.current) {
        ListGroup {
            Text(
                text = "First row",
                modifier = Modifier.padding(all = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            ListGroupDivider()
            Text(
                text = "Second row",
                modifier = Modifier.padding(all = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
