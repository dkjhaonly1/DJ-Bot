import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.deepak.djbot"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.deepak.djbot"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Read the API key from local.properties
        val localProperties = Properties().apply {
            load(rootProject.file("local.properties").inputStream())
        }
        val apiKey = localProperties.getProperty("apiKey", "")

        // Add API key to BuildConfig
        buildConfigField("String", "apiKey", "\"$apiKey\"")
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

    // Enable DataBinding and BuildConfig
    buildFeatures {
        dataBinding = true
        buildConfig = true  // Enable BuildConfig feature
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // dependencies of lottie
    implementation("com.airbnb.android:lottie:6.5.0")

    // dependencies of retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    // Dependencies of Gson
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Dependency for the Google AI client SDK for Android
    implementation("com.google.ai.client.generativeai:generativeai:0.7.0")
}
