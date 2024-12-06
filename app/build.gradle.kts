allprojects{
    repositories{
        google()
        mavenCentral()
        maven{url = uri("https://jitpack.io")}
    }
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {

    namespace = "com.example.instituteofthesouthpacific"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.instituteofthesouthpacific"
        minSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core libraries
    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")

    // Network library
    implementation ("com.android.volley:volley:1.2.1")

    // RecyclerView and CardView
    implementation ("androidx.recyclerview:recyclerview:1.3.1")
    implementation ("androidx.cardview:cardview:1.0.0")

    // Lifecycle components 
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.activity:activity-ktx:1.7.2")

    // Compose
    implementation ("androidx.compose.ui:ui:1.5.1")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.1")
    implementation ("androidx.compose.material3:material3:1.2.0")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.5.1")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.5.1")

    // Testing
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
}
