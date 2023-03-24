// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    }
}

// TODO: Remove once https://youtrack.jetbrains.com/issue/KTIJ-19369 is fixed
plugins {
    @Suppress(names = ["DSL_SCOPE_VIOLATION"])
    alias(libs.plugins.android.application) apply false
    @Suppress(names = ["DSL_SCOPE_VIOLATION"])
    alias(libs.plugins.android.library) apply false
    @Suppress(names = ["DSL_SCOPE_VIOLATION"])
    alias(libs.plugins.kotlin.android) apply false
    @Suppress(names = ["DSL_SCOPE_VIOLATION"])
    alias(libs.plugins.kotlin.jvm) apply false
    @Suppress(names = ["DSL_SCOPE_VIOLATION"])
    alias(libs.plugins.rust.android)
    @Suppress(names = ["DSL_SCOPE_VIOLATION"])
    alias(libs.plugins.sonarqube)

    // Maven publishing
    id("com.vanniktech.maven.publish") version "0.22.0"
}

val launchTask = gradle
        .startParameter
        .taskRequests
        .toString()
        .toLowerCase()

if (launchTask.contains("coverage", ignoreCase = true)) {
    apply {
        from("coverage.gradle")
    }
}
