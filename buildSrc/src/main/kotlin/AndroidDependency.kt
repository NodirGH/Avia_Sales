
object AndroidDependency {
    const val core = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val material = "com.google.android.material:material:${Versions.design}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.supportLibrary}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltKapt = "com.google.dagger:hilt-compiler:${Versions.hilt}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideProccessor = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val glideOkHttpIntegration =
        "com.github.bumptech.glide:okhttp3-integration:${Versions.glideOkhhtpIntegration}"
    const val shimmerEffect = "com.facebook.shimmer:shimmer:${Versions.shimmerEffect}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleViewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val navigationComponent =
        "androidx.navigation:navigation-fragment:${Versions.navigationComponent}"
    const val navigationUiKtx =
        "androidx.navigation:navigation-ui:${Versions.navigationComponent}"

    const val chucker = "com.github.chuckerteam.chucker:library:${Versions.chucker}"
    const val chuckerNoOp = "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"

}