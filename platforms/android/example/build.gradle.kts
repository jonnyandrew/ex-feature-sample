
plugins {
    id("io.element.android-compose-application")
    id("kotlin-android")
    id("kotlin-parcelize")
}

android {
    namespace = "io.element.android.feature.poc"
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = "io.element.android.feature.poc"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = Versions.versionCode
        versionName = Versions.versionName
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(projects.library)
    implementation(libs.elementx.extensionsdk)
    implementation(libs.androidx.corektx)
    implementation(libs.androidx.material)
    implementation(libs.appyx.core)
    coreLibraryDesugaring(libs.android.desugarjdklibs)
}
