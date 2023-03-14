package io.element.android.feature

import io.element.modulesdk.ElementFeatureProvider
import io.element.modulesdk.lifecycle.LifecycleModule
import timber.log.Timber
import uniffi.ex_feature.ExFeature
import uniffi.ex_feature.newExFeature

class SampleElementFeatureProvider : ElementFeatureProvider {
    private val rustImpl by lazy { newExFeature() }
    private val logger by lazy { Logger(rustImpl) }

    init {
        logger.log("Initializing sample feature module")
    }

    override fun lifecycleModule(): LifecycleModule =
        SampleLifecycleModule(logger)
}

internal class SampleLifecycleModule(
    private val logger: Logger,
) : LifecycleModule {
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
