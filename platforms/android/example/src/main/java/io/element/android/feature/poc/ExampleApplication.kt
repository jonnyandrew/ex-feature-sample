package io.element.android.feature.poc

import android.app.Application
import com.google.android.material.color.DynamicColors
import timber.log.Timber

class ExampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        Timber.plant(Timber.DebugTree())
    }
}
