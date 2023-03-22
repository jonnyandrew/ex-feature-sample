package io.element.android.feature

import io.element.extension.ElementExtensionProvider
import io.element.extension.lifecycle.LifecycleExtension
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
}

internal class SampleLifecycleModule(
    private val logger: Logger,
) : LifecycleExtension {
    override fun onCreate() {
        logger.log("onCreate")
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
