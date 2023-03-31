package io.element.android.feature.poc

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeComponentActivity
import io.element.android.feature.SampleElementExtensionProvider
import io.element.extension.lifecycle

class MainActivity : NodeComponentActivity() {

    private lateinit var extensionProvider: SampleElementExtensionProvider

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        extensionProvider = SampleElementExtensionProvider()
        extensionProvider.lifecycle().forEach { it.onCreate() }

        setContent {
            MaterialTheme {
                NodeHost(integrationPoint = appyxIntegrationPoint) {
                    RootNode(
                        buildContext = it,
                        extensionProvider = extensionProvider,
                        onInteractionComplete = { onInteractionComplete(this) }
                    )
                }
            }
        }
    }

    private fun onInteractionComplete(context: Context) =
        Toast.makeText(
            context,
            "Interaction complete",
            Toast.LENGTH_SHORT
        ).show()
}
