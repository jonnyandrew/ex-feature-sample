package io.element.android.feature

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.element.android.libraries.designsystem.components.dialogs.ConfirmationDialog
import io.element.android.libraries.designsystem.theme.components.Surface
import io.element.android.libraries.designsystem.theme.components.Text
import io.element.extension.login.LoginExtension

internal class SampleLoginExtension : LoginExtension {

    @Composable
    override fun Branding() {
        var isConfirmationDisplayed by rememberSaveable { mutableStateOf(false) }
        var isInfoDisplayed by rememberSaveable { mutableStateOf(false) }

        BrandingContent(
            isConfirmationDisplayed,
            isInfoDisplayed,
            { isConfirmationDisplayed = it },
            { isInfoDisplayed = it },
        )
    }

    @Composable
    private fun BrandingContent(
        isConfirmationDisplayed: Boolean,
        isInfoDisplayed: Boolean,
        setIsConfirmationDisplayed: (Boolean) -> Unit,
        setIsInfoDisplayed: (Boolean) -> Unit,
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .clickable {
                    setIsConfirmationDisplayed(true)
                },
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                text = """
                |Want to learn about extensions?
                | \ (•◡•) /
                | """.trimMargin().trim(),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
            )
        }

        if (isConfirmationDisplayed) {
            ConfirmationDialog(
                title = "Really learn more?",
                content = "Confirm to learn more about our customisations...",
                onSubmitClicked = {
                    setIsInfoDisplayed(true)
                    setIsConfirmationDisplayed(false)
                },
                onDismiss = { setIsConfirmationDisplayed(false) }
            )
        }

        if (isInfoDisplayed) {
            LearnMoreScreen(
                onDismiss = { setIsInfoDisplayed(false) },
            )
        }
    }
}
