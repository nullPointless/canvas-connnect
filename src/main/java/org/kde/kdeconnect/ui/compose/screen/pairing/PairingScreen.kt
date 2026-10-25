/*
 * SPDX-FileCopyrightText: 2026 Saul Cintero Chocarro <scintero@gmail.com>
 * SPDX-FileCopyrightText: 2026 nullPointless
 *
 * SPDX-License-Identifier: GPL-2.0-only OR GPL-3.0-only OR LicenseRef-KDE-Accepted-GPL
 */

package org.kde.kdeconnect.ui.compose.screen.pairing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.kde.kdeconnect.extensions.safeDrawingBottomPadding
import org.kde.kdeconnect.ui.compose.KdeTheme
import org.kde.kdeconnect.ui.compose.CanvasAccents
import org.kde.kdeconnect.ui.compose.canvasStatusColors
import org.kde.kdeconnect.ui.compose.components.DeviceRow
import org.kde.kdeconnect.ui.compose.components.IconBadge
import org.kde.kdeconnect.ui.compose.components.KdeThemePreviews
import org.kde.kdeconnect.ui.compose.components.ListGroup
import org.kde.kdeconnect.ui.compose.components.ListGroupDivider
import org.kde.kdeconnect.ui.compose.components.SectionHeader
import org.kde.kdeconnect.ui.compose.model.device.DeviceUiModel
import org.kde.kdeconnect_tp.R

@Composable
fun PairingScreen(
    uiState: PairingUiState,
    onClick: (String) -> Unit,
    onWifiSettingsClick: () -> Unit = {},
    onNotificationSettingsClick: () -> Unit = {},
    onLocalNetworkPermissionClick: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    val lazyListState = rememberLazyListState()
    val pullRefreshState = rememberPullToRefreshState()

    PairingScreenContent(
        lazyListState = lazyListState,
        pullRefreshState = pullRefreshState,
        uiState = uiState,
        onClick = onClick,
        onWifiSettingsClick = onWifiSettingsClick,
        onNotificationSettingsClick = onNotificationSettingsClick,
        onLocalNetworkPermissionClick = onLocalNetworkPermissionClick,
        onRefresh = onRefresh
    )
}

@Composable
private fun PairingScreenContent(
    lazyListState: LazyListState,
    pullRefreshState: PullToRefreshState,
    uiState: PairingUiState,
    onClick: (String) -> Unit,
    onWifiSettingsClick: () -> Unit,
    onNotificationSettingsClick: () -> Unit,
    onLocalNetworkPermissionClick: () -> Unit,
    onRefresh: () -> Unit = {}
) {
    val status = canvasStatusColors()
    val bottomInset = safeDrawingBottomPadding().calculateBottomPadding()

    PullToRefreshBox(
        isRefreshing = uiState.isRefreshing,
        onRefresh = onRefresh,
        state = pullRefreshState
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp + bottomInset),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            // Explanations, permission prompts and network notices
            item {
                PairingExplanations(
                    uiState = uiState,
                    onWifiSettingsClick = onWifiSettingsClick,
                    onNotificationSettingsClick = onNotificationSettingsClick,
                    onLocalNetworkPermissionClick = onLocalNetworkPermissionClick
                )
            }

            // Connected devices
            item {
                SectionHeader(title = stringResource(id = R.string.category_connected_devices))
            }
            if (uiState.connected.isEmpty()) {
                item {
                    EmptyPlaceholder(modifier = Modifier.padding(horizontal = 16.dp))
                }
            } else {
                item {
                    DeviceGroup(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        devices = uiState.connected,
                        statusText = { stringResource(id = R.string.device_status_connected) },
                        statusColor = status.connected,
                        onClick = onClick
                    )
                }
            }

            // Available devices
            if (uiState.available.isNotEmpty()) {
                item {
                    SectionHeader(title = stringResource(id = R.string.category_not_paired_devices))
                }
                item {
                    DeviceGroup(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        devices = uiState.available,
                        statusText = { stringResource(id = R.string.device_status_not_paired) },
                        statusColor = MaterialTheme.colorScheme.primary,
                        onClick = onClick
                    )
                }
            }

            // Remembered devices
            if (uiState.remembered.isNotEmpty()) {
                item {
                    SectionHeader(title = stringResource(id = R.string.category_remembered_devices))
                }
                item {
                    DeviceGroup(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        devices = uiState.remembered,
                        statusText = { stringResource(id = R.string.device_status_offline) },
                        statusColor = status.neutral,
                        onClick = onClick
                    )
                }
            }
        }
    }
}

/** One section of devices rendered as an inset grouped list. */
@Composable
private fun DeviceGroup(
    devices: List<DeviceUiModel>,
    statusText: @Composable (DeviceUiModel) -> String,
    statusColor: Color,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val status = canvasStatusColors()
    ListGroup(modifier = modifier) {
        devices.forEachIndexed { index, device ->
            DeviceRow(
                device = device,
                onClick = { onClick(device.id) },
                statusText = if (device.summaryRes > 0) {
                    stringResource(id = device.summaryRes)
                } else {
                    statusText(device)
                },
                statusColor = if (device.summaryRes > 0) {
                    status.warning
                } else {
                    statusColor
                },
            )
            if (index < devices.lastIndex) {
                ListGroupDivider()
            }
        }
    }
}

@Composable
private fun PairingExplanations(
    uiState: PairingUiState,
    onWifiSettingsClick: () -> Unit,
    onNotificationSettingsClick: () -> Unit,
    onLocalNetworkPermissionClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (!uiState.hasLocalNetworkPermission) {
            NoticeCard(
                text = stringResource(id = R.string.pairing_missing_local_network_permission),
                iconRes = R.drawable.ic_warning,
                accent = CanvasAccents.orange,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                onClick = onLocalNetworkPermissionClick
            )
        }

        if (!uiState.hasNotificationsPermission) {
            NoticeCard(
                text = stringResource(R.string.no_notifications),
                iconRes = R.drawable.ic_warning,
                accent = CanvasAccents.orange,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                onClick = onNotificationSettingsClick
            )
        }

        if (uiState.hasDuplicateNames) {
            NoticeCard(
                text = stringResource(id = R.string.pairing_duplicate_names),
                iconRes = R.drawable.ic_warning,
                accent = CanvasAccents.orange,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            )
        }

        val someDevicesReachable = uiState.available.isNotEmpty() || uiState.connected.isNotEmpty()

        if (uiState.hasLocalNetworkPermission && uiState.hasNotificationsPermission) {
            when {
                !someDevicesReachable && !uiState.isWifiAvailable -> NoticeCard(
                    text = stringResource(R.string.no_wifi),
                    iconRes = R.drawable.ic_wifi,
                    accent = CanvasAccents.blue,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    onClick = onWifiSettingsClick,
                    chevron = true,
                )

                !uiState.isTrustedNetwork -> NoticeCard(
                    text = stringResource(R.string.on_non_trusted_message),
                    iconRes = R.drawable.ic_warning,
                    accent = CanvasAccents.orange,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                )

                else -> HintCaption(text = stringResource(R.string.pairing_description))
            }
        }
    }
}

/** A soft, tinted callout card with a small icon badge; optionally tappable. */
@Composable
fun NoticeCard(
    text: String,
    iconRes: Int,
    accent: Color,
    containerColor: Color,
    contentColor: Color,
    onClick: (() -> Unit)? = null,
    chevron: Boolean = false,
) {
    val shape = RoundedCornerShape(16.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            )
            .background(containerColor, shape)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconBadge(iconRes = iconRes, tint = accent, size = 28.dp, iconScale = 0.6f)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = contentColor,
            modifier = Modifier.weight(1f),
        )
        if (chevron) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = contentColor.copy(alpha = 0.5f),
            )
        }
    }
}

/** A quiet caption used for informational text (no card, no icon). */
@Composable
fun HintCaption(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 10.dp),
    )
}

/** Empty state shown inside a muted grouped card, iOS-style. */
@Composable
fun EmptyPlaceholder(modifier: Modifier = Modifier) {
    ListGroup(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 28.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.device_list_empty),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@KdeThemePreviews
@Composable
private fun PreviewCompose() {
    KdeTheme(context = androidx.compose.ui.platform.LocalContext.current) {
        val lazyListState = rememberLazyListState()
        val pullRefreshState = rememberPullToRefreshState()

        PairingScreenContent(
            lazyListState = lazyListState,
            pullRefreshState = pullRefreshState,
            uiState = PairingUiState(
                isWifiAvailable = true,
                hasNotificationsPermission = true,
                hasLocalNetworkPermission = true,
                isTrustedNetwork = true,
                hasDuplicateNames = false,
                connected = listOf(
                    DeviceUiModel(
                        id = "_2504584b_6aa2_3cd6_bd1b_5e958aa6cd23_",
                        icon = R.drawable.ic_device_laptop_32dp,
                        name = "Device 1",
                        summaryRes = 0,
                        isReachable = true,
                        isPaired = true
                    ),
                ),
                available = listOf(
                    DeviceUiModel(
                        id = "_2504584b_6aa2_3cd6_bd1b_5e958aa6cd24_",
                        icon = R.drawable.ic_device_desktop_32dp,
                        name = "Device 2",
                        summaryRes = R.string.protocol_version_newer,
                        isReachable = true,
                        isPaired = false
                    )
                ),
                remembered = emptyList(),
                isRefreshing = false
            ),
            onClick = { /* Do nothing */ },
            onWifiSettingsClick = { /* Do nothing */ },
            onNotificationSettingsClick = { /* Do nothing */ },
            onLocalNetworkPermissionClick = { /* Do nothing */ },
            onRefresh = { /* Do nothing */ }
        )
    }
}
