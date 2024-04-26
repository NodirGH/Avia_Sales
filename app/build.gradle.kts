plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = ConfigData.compileSdk


    defaultConfig {
        applicationId = ConfigData.applicationId
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdk
        versionCode = ConfigData.versionCode
        versionName = ConfigData.releaseVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        viewBinding = true
    }
}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":toolkit"))

    implementation(AndroidDependency.core)
    implementation(AndroidDependency.appCompat)
    implementation(AndroidDependency.material)
    implementation(AndroidDependency.constraintlayout)

    implementation(AndroidDependency.hilt)
    kapt(AndroidDependency.hiltKapt)

    implementation(AndroidDependency.timber)
    implementation(AndroidDependency.glide)
    implementation(AndroidDependency.glideProccessor)
    implementation(AndroidDependency.glideOkHttpIntegration)
    implementation(AndroidDependency.shimmerEffect)

    implementation(AndroidDependency.lifecycleViewModelKtx)
    implementation(AndroidDependency.navigationUiKtx)
    implementation(AndroidDependency.navigationComponent)

    implementation(TestDependencies.junit)
    implementation(TestDependencies.junitExt)
    implementation(TestDependencies.espresso)

    implementation(AndroidDependency.lifecycleRuntime)
    implementation(AndroidDependency.lifecycleViewModelKtx)
}