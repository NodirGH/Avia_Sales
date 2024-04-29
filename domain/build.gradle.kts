plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = ConfigData.compileSdk

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(AndroidDependency.core)
    implementation(AndroidDependency.appCompat)
    implementation(AndroidDependency.material)
    testImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.junitExt)
    androidTestImplementation(TestDependencies.espresso)

    implementation(AndroidDependency.hilt)
    kapt(AndroidDependency.hiltKapt)

    debugImplementation(AndroidDependency.chucker)
    releaseImplementation(AndroidDependency.chuckerNoOp)

    implementation(OkhttpDependency.okhttp)
    implementation(OkhttpDependency.okhttpLogger)
    implementation(RetrofitDependency.retrofit)
    implementation(RetrofitDependency.gsonRetrofit)

    implementation(CoroutineDependency.core)
}