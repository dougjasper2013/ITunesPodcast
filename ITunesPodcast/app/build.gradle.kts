plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // alias(libs.plugins.kotlin.kapt)
    kotlin("kapt")
}

android {
    namespace = "com.trios2025dj.itunespodcast"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.trios2025dj.itunespodcast"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Glide
    implementation(libs.glide)
    kapt(libs.glide.compiler)

    // Gson (optional — Retrofit uses it, but standalone if needed)
    implementation(libs.gson)

    // RecyclerView
    implementation(libs.recyclerview)

    // Lifecycle ViewModel
    implementation(libs.lifecycle.viewmodel)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}