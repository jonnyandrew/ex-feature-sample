package io.element.android.feature

import android.content.Context
import android.os.Handler
import android.widget.Toast
import io.element.extension.sync.SlidingSyncExtension
import uniffi.ex_feature.ExFeature
import uniffi.ex_feature.SlidingSyncUpdate

internal class SampleSlidingSyncExtension(
    private val context: Context,
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

        Handler(context.mainLooper).post {
            Toast.makeText(context, processedUpdate, Toast.LENGTH_SHORT).show()
        }
    }
}