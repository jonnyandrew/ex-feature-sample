package io.element.android.feature

import io.element.extension.ElementExtension
import io.element.extension.ElementExtensionProvider
import io.element.extension.login.LoginExtension
import uniffi.ex_feature.newExFeature

class SampleElementExtensionProvider : ElementExtensionProvider {
    private val rustImpl by lazy { newExFeature() }
    private val logger by lazy { Logger(rustImpl) }

    init {
        logger.log("Initializing sample feature module")
    }

    override fun extensions(): List<ElementExtension> = listOf(
        SampleLifecycleExtension(logger),
        SampleLoginExtension(),
        SampleOnboardingExtension1(),
        SampleOnboardingExtension2(),
    )
}
