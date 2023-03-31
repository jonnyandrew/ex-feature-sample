package io.element.android.feature.poc

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class NavTarget : Parcelable {
    @Parcelize
    data class Onboarding(val index: Int) : NavTarget()

    @Parcelize
    object Login : NavTarget()
}
