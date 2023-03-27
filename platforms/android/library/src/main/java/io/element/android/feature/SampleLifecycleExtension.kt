package io.element.android.feature

import io.element.extension.lifecycle.LifecycleExtension
import timber.log.Timber
import uniffi.ex_feature.ExFeature

internal class SampleLifecycleExtension(
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
