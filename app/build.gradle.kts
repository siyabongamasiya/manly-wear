plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id ("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    alias(libs.plugins.google.gms.google.services)

}

android {
    namespace = "ecommerce.project.manlywear"
    compileSdk = 34

    defaultConfig {
        applicationId = "ecommerce.project.manlywear"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation("androidx.compose.animation:animation:1.6.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // WorkManager with Hilt
    implementation ("androidx.hilt:hilt-work:1.0.0")
    implementation ("androidx.work:work-runtime-ktx:2.7.1")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")

    //Room
    //dependencies
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.work)
    kapt (libs.androidx.room.compiler)

    //dagger hilt
    implementation(libs.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    // Hilt testing (optional, for testing with Hilt)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.dagger.hilt.android.compiler)
    // Hilt Navigation Compose
    implementation (libs.androidx.hilt.navigation.compose)

    //Glide
    implementation (libs.compose)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //material extended icons
    implementation(libs.androidx.material.icons.extended.android)

    //core navigation library
    implementation(libs.androidx.navigation.compose)
    androidTestImplementation(libs.androidx.navigation.testing)

    //type-safe nav
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    testImplementation ("io.mockk:mockk:1.13.5")
    testImplementation ("app.cash.turbine:turbine:0.12.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}