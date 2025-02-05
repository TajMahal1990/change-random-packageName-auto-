plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 29
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
}
tasks.register("changePackageName") {
    doLast {
        val newPackageName = "com.${(1..10).map { "abcdefghijklmnopqrstuvwxyz0123456789".random() }.joinToString("")}"
        val manifestFile = file("src/main/AndroidManifest.xml")

        val manifestText = manifestFile.readText()
            .replace(Regex("package=\"[^\"]+\""), "package=\"$newPackageName\"")
        manifestFile.writeText(manifestText)

        val gradleFile = file("build.gradle.kts")
        val gradleText = gradleFile.readText()
            .replace(Regex("applicationId\\s+\"[^\"]+\""), "applicationId \"$newPackageName\"")
        gradleFile.writeText(gradleText)

        // Вывод в Run и Gradle Console
        logger.lifecycle("✅ Новый package name: $newPackageName")
    }
}


