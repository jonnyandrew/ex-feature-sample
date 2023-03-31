package io.element.android.feature.poc.login

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import io.element.android.feature.SampleElementExtensionProvider
import io.element.android.feature.poc.LoginScreen

class LoginNode(
    buildContext: BuildContext,
    private val extensionProvider: SampleElementExtensionProvider,
    private val onInteractionComplete: (Context) -> Unit
) : Node(
    buildContext = buildContext
) {
    @Composable
    override fun View(modifier: Modifier) {
        LoginScreen(
            extensionProvider,
            onInteractionComplete,
        )
    }
}

