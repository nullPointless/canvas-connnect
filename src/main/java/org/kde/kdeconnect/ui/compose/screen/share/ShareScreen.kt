/*
 * SPDX-FileCopyrightText: 2026 Saul Cintero Chocarro <scintero@gmail.com>
 *
 * SPDX-License-Identifier: GPL-2.0-only OR GPL-3.0-only OR LicenseRef-KDE-Accepted-GPL
 */

package org.kde.kdeconnect.ui.compose.screen.share

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.kde.kdeconnect.extensions.safeDrawingBottomPadding
import org.kde.kdeconnect.ui.compose.KdeTheme
import org.kde.kdeconnect.ui.compose.canvasStatusColors
import org.kde.kdeconnect.ui.compose.components.DeviceRow
import org.kde.kdeconnect.ui.compose.components.KdeThemePreviews
import org.kde.kdeconnect.ui.compose.components.ListGroup
import org.kde.kdeconnect.ui.compose.components.ListGroupDivider
import org.kde.kdeconnect.ui.compose.components.SectionHeader
import org.kde.kdeconnect.ui.compose.model.device.DeviceUiModel
import org.kde.kdeconnect_tp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareScreen(
    devices: List<DeviceUiModel>,
    intentHasUrl: Boolean,
    isRefreshing: Boolean,
    onDeviceClick: (String) -> Unit,
    onRefresh: () -> Unit
) {
    val state = rememberLazyListState()
    val sectionTitle = if (intentHasUrl) {
        "${stringResource(id = R.string.unreachable_device_url_share_text)} ${stringResource(id = R.string.share_to)}"
    } else {
        stringResource(id = R.string.share_to)
    }

    ShareScreenContent(
        state = state,
        isRefreshing = isRefreshing,
        sectionTitle = sectionTitle,
        devices = devices,
        onDeviceClick = onDeviceClick,
        onRefresh = onRefresh
    )
}

@Composable
private fun ShareScreenContent(
    state: LazyListState,
    isRefreshing: Boolean,
    sectionTitle: String,
    devices: List<DeviceUiModel>,
    onDeviceClick: (String) -> Unit,
    onRefresh: () -> Unit
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp),
            contentPadding = safeDrawingBottomPadding(),
            state = state
        ) {
            item {
                SectionHeader(title = sectionTitle)
            }
            item {
                val status = canvasStatusColors()
                ListGroup(modifier = Modifier.padding(horizontal = 16.dp)) {
                    devices.forEachIndexed { index, device ->
                        DeviceRow(
                            device = device,
                            onClick = { onDeviceClick(device.id) },
                            statusText = if (!device.isReachable) {
                                stringResource(id = R.string.runcommand_notreachable)
                            } else {
                                null
                            },
                            statusColor = status.error,
                        )
                        if (index < devices.lastIndex) {
                            ListGroupDivider()
                        }
                    }
                }
            }
        }
    }
}

@KdeThemePreviews
@Composable
private fun ShareScreenPreview() {
    KdeTheme(context = LocalContext.current) {
        ShareScreenContent(
            state = rememberLazyListState(),
            isRefreshing = false,
            sectionTitle = stringResource(id = R.string.share_to),
            devices = listOf(
                DeviceUiModel(
                    id = "_2504584b_6aa2_3cd6_bd1b_5e958aa6cd23_",
                    icon = R.drawable.ic_device_laptop_32dp,
                    name = "Device 1",
                    summaryRes = 0,
                    isReachable = true,
                    isPaired = true,
                ),
                DeviceUiModel(
                    id = "_2504584b_6aa2_3cd6_bd1b_5e958aa6cd24_",
                    icon = R.drawable.ic_device_phone_32dp,
                    name = "Device 2",
                    summaryRes = 0,
                    isReachable = true,
                    isPaired = true,
                )
            ),
            onDeviceClick = { /* Do nothing */ },
            onRefresh = { /* Do nothing */ }
        )
    }
}
