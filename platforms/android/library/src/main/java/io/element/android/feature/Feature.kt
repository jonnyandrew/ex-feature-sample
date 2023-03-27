package io.element.android.feature

import io.element.extension.ElementExtensionProvider
import io.element.extension.lifecycle.LifecycleExtension
import io.element.extension.login.LoginExtension
import uniffi.ex_feature.newExFeature

class SampleElementExtensionProvider : ElementExtensionProvider {
    private val rustImpl by lazy { newExFeature() }
    private val logger by lazy { Logger(rustImpl) }

    init {
        logger.log("Initializing sample feature module")
    }

    override fun lifecycle(): LifecycleExtension =
        SampleLifecycleExtension(logger)

    override fun login(): LoginExtension =
        SampleLoginExtension()
}
