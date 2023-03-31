package io.element.android.feature

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import io.element.android.libraries.designsystem.theme.components.Text
import io.element.extension.onboarding.OnboardingExtension

internal class SampleOnboardingExtension1 : OnboardingExtension {
    override fun onboardingNode(buildContext: BuildContext, onNext: () -> Unit): Node =
        ExtraOnboarding(
            buildContext,
            text = "Welcome to ElementX!\nTap to continue",
            onNext
        )

}

internal class SampleOnboardingExtension2 : OnboardingExtension {
    override fun onboardingNode(buildContext: BuildContext, onNext: () -> Unit): Node =
        ExtraOnboarding(
            buildContext,
            text = "This onboarding was added by the sample extension.",
            onNext
        )
}

class ExtraOnboarding(
    buildContext: BuildContext,
    private val text: String,
    private val onNext: () -> Unit,
) : Node(
    buildContext,
) {
    @Composable
    override fun View(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .clickable { onNext() }
                .padding(24.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                modifier = modifier,
                textAlign = TextAlign.Center
            )
        }
    }
}
