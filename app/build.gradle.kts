plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.google.hilt)
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id ("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.mealsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mealsapp"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
//    implementation(libs.androidx.room.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Room
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")

    //Glide
    implementation(libs.glide)


    implementation ("androidx.recyclerview:recyclerview:1.2.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")

    //Fragment
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment)
    implementation(libs.activity)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // SDP && SSP
    implementation ("com.intuit.ssp:ssp-android:1.1.0")
    implementation ("com.intuit.sdp:sdp-android:1.1.0")

    // Hilt
    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.android.compiler)
    kapt(libs.hilt.compiler)
    kapt(libs.google.hilt.android.compiler)

    //gif
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.17")

    //material Design
    implementation ("com.google.android.material:material:1.3.0-alpha03")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    //dots indicator
    implementation("com.tbuonomo:dotsindicator:5.0")

    //Mockito (Testing)
    androidTestImplementation(libs.mockito.mockito.core)
    androidTestImplementation(libs.jetbrains.kotlinx.coroutines.test)
    androidTestImplementation(libs.mockk.mockk)
    androidTestImplementation(libs.kotlin.mockito.kotlin)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.runner)

    testImplementation(libs.mockito.mockito.core)
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation(libs.mockk.mockk) // MockK
    testImplementation(libs.kotlin.mockito.kotlin)
    testImplementation(libs.truth)
    testImplementation(libs.junit.jupiter)

    testImplementation(libs.junit)
    testImplementation(libs.junit.v113)
    testImplementation(libs.runner.v140)
    implementation(libs.material.v120)

    // Unit Test
    testImplementation(libs.junit)

    // Android Test
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation ("com.jakewharton.threetenabp:threetenabp:1.3.1")

}