package io.element.android.feature

import uniffi.ex_feature.newExModule

object Feature {
    val module by lazy {
        newExModule()
    }
}
