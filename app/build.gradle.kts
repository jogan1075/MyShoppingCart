plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "com.jmc.myshoppingcart"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jmc.myshoppingcart"
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += setOf(
//                "META-INF/LICENSE",
//                "META-INF/LICENSE.md",
//                "META-INF/LICENSE.txt",
//                "META-INF/NOTICE",
//                "META-INF/NOTICE.txt"

                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/DEPENDENCIES"
            )
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

    implementation("com.github.jogan1075:Core:1.0.0")
    implementation("io.coil-kt:coil-compose:2.2.0")
    //hilt
    implementation(libs.daggerHilt)
    implementation(libs.hiltNavCompose)
    ksp(libs.hiltCompiler)
    ksp(libs.hiltAndroidCompiler)
    ksp(libs.hiltCompiler)

    implementation(libs.mockk)
    implementation(libs.mockitoCore)
    implementation(libs.mockitoInline)
    implementation(libs.mockitoKotlin)
    implementation(libs.testRules)
    implementation(libs.coroutineTest)
    testImplementation(kotlin("test"))

    testImplementation ("androidx.arch.core:core-testing:2.1.0")
}