/*
 * SPDX-FileCopyrightText: 2026 Saul Cintero Chocarro <scintero@gmail.com>
 * SPDX-FileCopyrightText: 2026 nullPointless
 *
 * SPDX-License-Identifier: GPL-2.0-only OR GPL-3.0-only OR LicenseRef-KDE-Accepted-GPL
 */

package org.kde.kdeconnect.ui.compose.screen.device

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.kde.kdeconnect.PairingHandler
import org.kde.kdeconnect.ui.compose.KdeTheme
import org.kde.kdeconnect.ui.compose.components.IconBadge
import org.kde.kdeconnect.ui.compose.components.KdeThemePreviews
import org.kde.kdeconnect.ui.compose.CanvasAccents
import org.kde.kdeconnect_tp.R

@Composable
fun DevicePairingScreen(
    pairStatus: PairingHandler.PairState,
    verificationKey: String,
    pairMessage: String? = null,
    onRequestPairing: () -> Unit,
    onAcceptPairing: () -> Unit,
    onRejectPairing: () -> Unit
) {
    DevicePairingScreenContent(
        pairStatus = pairStatus,
        verificationKey = verificationKey,
        pairMessage = pairMessage,
        onRequestPairing = onRequestPairing,
        onAcceptPairing = onAcceptPairing,
        onRejectPairing = onRejectPairing
    )
}

@Composable
private fun DevicePairingScreenContent(
    pairStatus: PairingHandler.PairState,
    verificationKey: String,
    pairMessage: String? = null,
    onRequestPairing: () -> Unit,
    onAcceptPairing: () -> Unit,
    onRejectPairing: () -> Unit
) {
    val bottomPadding = org.kde.kdeconnect.extensions.safeDrawingBottomPadding().calculateBottomPadding()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp + bottomPadding),
        verticalArrangement = Arrangement.Center,
    ) {
        val finalPairMessage = pairMessage ?: when (pairStatus) {
            PairingHandler.PairState.NotPaired -> stringResource(id = R.string.device_not_paired)
            else -> stringResource(id = R.string.pair_requested)
        }

        // Hero: key icon badge
        IconBadge(
            iconRes = R.drawable.ic_key,
            tint = CanvasAccents.blue,
            size = 56.dp,
            modifier = Modifier.padding(bottom = 20.dp),
        )

        Text(
            text = finalPairMessage,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = stringResource(id = R.string.pairing_explanation),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        if (pairStatus != PairingHandler.PairState.NotPaired) {
            VerificationKeyCard(verificationKey)
        }

        if (pairStatus == PairingHandler.PairState.Requested) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        if (pairStatus == PairingHandler.PairState.NotPaired) {
            Button(
                onClick = onRequestPairing,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.request_pairing),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }

        if (pairStatus == PairingHandler.PairState.RequestedByPeer) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Button(
                    onClick = onAcceptPairing,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.pairing_accept),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedButton(
                    onClick = onRejectPairing,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.pairing_reject),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}

/** The verification key, displayed large and scannable like a pairing code. */
@Composable
private fun VerificationKeyCard(verificationKey: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_key),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.pairing_verification_key_label),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = verificationKey,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}

@KdeThemePreviews
@Composable
private fun DevicePairingScreenNotPairedPreview() {
    KdeTheme(context = LocalContext.current) {
        DevicePairingScreenContent(
            pairStatus = PairingHandler.PairState.NotPaired,
            verificationKey = "",
            onRequestPairing = { /* Do nothing */ },
            onAcceptPairing = { /* Do nothing */ },
            onRejectPairing = { /* Do nothing */ }
        )
    }
}

@KdeThemePreviews
@Composable
private fun DevicePairingScreenRequestedPreview() {
    KdeTheme(context = LocalContext.current) {
        DevicePairingScreenContent(
            pairStatus = PairingHandler.PairState.Requested,
            verificationKey = "123456",
            onRequestPairing = { /* Do nothing */ },
            onAcceptPairing = { /* Do nothing */ },
            onRejectPairing = { /* Do nothing */ }
        )
    }
}

@KdeThemePreviews
@Composable
private fun DevicePairingScreenRequestedByPeerPreview() {
    KdeTheme(context = LocalContext.current) {
        DevicePairingScreenContent(
            pairStatus = PairingHandler.PairState.RequestedByPeer,
            verificationKey = "123456",
            onRequestPairing = { /* Do nothing */ },
            onAcceptPairing = { /* Do nothing */ },
            onRejectPairing = { /* Do nothing */ }
        )
    }
}
