package io.element.android.feature

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.element.extension.ElementExtensionProvider
import io.element.extension.lifecycle.LifecycleExtension
import io.element.extension.login.LoginExtension
import timber.log.Timber
import uniffi.ex_feature.ExFeature
import uniffi.ex_feature.newExFeature

class SampleElementExtensionProvider : ElementExtensionProvider {
    private val rustImpl by lazy { newExFeature() }
    private val logger by lazy { Logger(rustImpl) }

    init {
        logger.log("Initializing sample feature module")
    }

    override fun lifecycle(): LifecycleExtension =
        SampleLifecycleModule(logger)

    override fun login(): LoginExtension =
        SampleLoginExtension()
}

internal class SampleLifecycleModule(
    private val logger: Logger,
) : LifecycleExtension {
    override fun onCreate() {
        logger.log("onCreate")
    }
}

internal class SampleLoginExtension : LoginExtension {
    @Composable
    override fun Branding() {
        Text(
            modifier = Modifier
                .border(3.dp, Color.Blue)
                .fillMaxWidth()
                .padding(10.dp),
            text = """
                |Corpland Systems
                | \ (•◡•) /
                | """.trimMargin().trim(),
            color = Color.Magenta,
            fontSize = 36.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
        )
    }
}

internal class Logger(
    private val rustImpl: ExFeature,
) {
    fun log(message: String) =
        Timber.tag(
            rustImpl.logTag()
        ).d("${rustImpl.name()}: $message")
}
