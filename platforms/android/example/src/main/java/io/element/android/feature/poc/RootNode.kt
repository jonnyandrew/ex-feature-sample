package io.element.android.feature.poc

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import io.element.android.feature.SampleElementExtensionProvider
import io.element.android.feature.poc.login.LoginNode
import io.element.extension.onboarding

class RootNode(
    buildContext: BuildContext,
    private val backStack: BackStack<NavTarget> = BackStack(
        initialElement = NavTarget.Onboarding(0),
        savedStateMap = buildContext.savedStateMap,
    ),
    private val extensionProvider: SampleElementExtensionProvider,
    private val onInteractionComplete: (Context) -> Unit
) : ParentNode<NavTarget>(
    navModel = backStack,
    buildContext = buildContext
) {

    override fun resolve(navTarget: NavTarget, buildContext: BuildContext): Node =
        when (navTarget) {
            NavTarget.Login ->
                LoginNode(buildContext, extensionProvider, onInteractionComplete)
            is NavTarget.Onboarding -> {
                val onboardingExtensions = extensionProvider.onboarding()
                val callback = {
                    val nextIndex = navTarget.index + 1
                    if (onboardingExtensions.size > nextIndex) {
                        backStack.push(NavTarget.Onboarding(nextIndex))
                    } else {
                        backStack.push(NavTarget.Login)
                    }
                }
                onboardingExtensions[navTarget.index].onboardingNode(
                    buildContext,
                    onNext = callback,
                )
            }
        }

    @Composable
    override fun View(modifier: Modifier) =
        Box {
            Children(
                navModel = backStack
            )
        }
}

