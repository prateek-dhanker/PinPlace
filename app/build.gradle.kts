plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.pinplace"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.pinplace"
        minSdk = 24
        targetSdk = 36
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.android.gms:play-services-location:21.2.0")
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // For RxJava support (optional)
    implementation("androidx.room:room-rxjava2:$room_version")

    // For lifecycle components
    implementation("androidx.lifecycle:lifecycle-viewmodel:$room_version")
    implementation("androidx.lifecycle:lifecycle-livedata:$room_version")
}