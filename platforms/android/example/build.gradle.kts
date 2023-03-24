
plugins {
    id("io.element.android-compose-application")
    id("kotlin-android")
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
    implementation(libs.extensionsdk)
    implementation(libs.androidx.corektx)
    implementation(libs.androidx.material)
    coreLibraryDesugaring(libs.android.desugarjdklibs)
}
