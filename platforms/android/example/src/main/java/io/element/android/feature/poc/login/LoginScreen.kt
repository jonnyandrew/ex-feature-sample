package io.element.android.feature.poc

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.element.extension.ElementExtensionProvider
import io.element.extension.login
import io.element.extension.slidingSync

@Composable
internal fun LoginScreen(
    extensionProvider: ElementExtensionProvider,
    onInteractionComplete: (context: Context) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        val context = LocalContext.current
        extensionProvider.login().forEach {
            it.Banner(onInteractionComplete = {
                onInteractionComplete(context)
            })
        }
        Button(
            onClick = {
            extensionProvider.slidingSync().forEach {
                it.onSlidingSyncUpdate(emptyList(), emptyList())
            }
        }) {
            Text(
                text = "Simulate Sliding Sync update"
            )
        }
    }
}

