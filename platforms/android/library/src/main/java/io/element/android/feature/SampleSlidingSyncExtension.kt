package io.element.android.feature

import io.element.extension.sync.SlidingSyncExtension
import uniffi.ex_feature.ExFeature
import uniffi.ex_feature.SlidingSyncUpdate

internal class SampleSlidingSyncExtension(
    private val rustImpl: ExFeature,
    private val logger: Logger,
): SlidingSyncExtension {
    override fun onSlidingSyncUpdate(lists: List<String>, rooms: List<String>) {
        val processedUpdate = rustImpl.processSlidingSyncUpdate(
            SlidingSyncUpdate(
                lists = lists,
                rooms = rooms,
            )
        )
        logger.log(processedUpdate)
    }
}