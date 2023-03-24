import extension.composeConfig
import extension.composeDependencies

plugins {
    id("io.element.android-library")
    id("org.jetbrains.kotlin.android")
    id("org.mozilla.rust-android-gradle.rust-android")
}

apply {
    plugin("com.vanniktech.maven.publish")
}

cargo {
    module = "../../../crates"       // Or whatever directory contains your Cargo.toml
    libname = "uniffi-ex-feature"          // Or whatever matches Cargo.toml"s [package] name.
    targets = listOf("arm", "x86", "x86_64", "arm64")
    targetIncludes = arrayOf("libuniffi_ex_feature.so")
    targetDirectory = "../../../target"
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
    }

    testCoverage {
        jacocoVersion = "0.8.8"
    }

    ndkVersion = Versions.ndk

    composeConfig(libs)
}

dependencies {
    implementation(libs.extensionsdk)
    implementation("net.java.dev.jna:jna:5.7.0@aar")
    composeDependencies(libs)
    coreLibraryDesugaring(libs.android.desugarjdklibs)
}

apply {
    from("./uniffi-bindgen.gradle")
}

afterEvaluate {
    val taskName = gradle.startParameter.taskRequests.toString()
    val isReleaseBuild = taskName.contains("release", ignoreCase = true) || taskName.contains("publish", ignoreCase = true)
    if (isReleaseBuild) {
        cargo.profile = "release"
    }
}

