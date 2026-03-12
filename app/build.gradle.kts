plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.mindmines"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mindmines"
        minSdk = 29
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (this.requested.group == "androidx.datastore") {
            if (this.requested.name == "datastore-preferences-jvm") {
                this.useTarget("androidx.datastore:datastore-preferences-android:${this.requested.version}")
            }
            if (this.requested.name == "datastore-jvm") {
                this.useTarget("androidx.datastore:datastore-android:${this.requested.version}")
            }
        }
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")

    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    implementation("com.google.code.gson:gson:2.13.2")
    implementation("androidx.security:security-crypto:1.1.0")

    implementation("androidx.datastore:datastore-preferences:1.1.0")
    implementation("com.google.guava:guava:32.0.1-android")
    implementation("androidx.datastore:datastore-preferences-rxjava3:1.1.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
}