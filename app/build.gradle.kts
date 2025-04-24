plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    alias(libs.plugins.kotlinxSeralization)
}

android {
    namespace = "com.example.gamesshare"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.gamesshare"
        minSdk = 24
        versionCode = 1
        targetSdk = 35
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
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Retrofit)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

    //dagger
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.0")
    implementation("com.google.firebase:firebase-storage:21.0.0")

    //customTextField
    implementation("com.github.JorgePR19:CustomTextField:1.1.0")

    //constraint
    implementation(libs.androidx.constraintlayout.compose)

    //coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.seralization.json)
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //dataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    //liveData
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.5")

    // Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.4.0")

    //paging 3
    implementation("androidx.paging:paging-runtime-ktx:3.3.2")
    implementation("androidx.paging:paging-compose:3.3.2")

    //video
    implementation("androidx.media3:media3-exoplayer:1.5.0")
    implementation("androidx.media3:media3-exoplayer-dash:1.5.0")
    implementation("androidx.media3:media3-ui:1.5.0")

    implementation("androidx.media3:media3-session:1.5.0") // [Required] MediaSession Extension dependency
    implementation("androidx.media3:media3-exoplayer-hls:1.5.0") // [Optional] If your media item is HLS (m3u8..)
    implementation("androidx.media3:media3-exoplayer-smoothstreaming:1.5.0") // [Optional] If your media item is smoothStreaming

    //icons
    implementation(libs.androidx.material.icons.extended)
}