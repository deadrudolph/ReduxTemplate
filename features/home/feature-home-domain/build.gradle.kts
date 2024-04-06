plugins {
    id("com.deadrudolph.android.library")
    id("com.deadrudolph.android.di")
}

android {
    namespace = "com.deadrudolph.feature_home_domain"
}

dependencies {
    implementation(projects.common.commonDi)
    implementation(projects.common.commonNetwork)
    implementation(projects.common.commonDomain)

    implementation(libs.dagger)
    implementation(libs.immutableList)
    ksp(libs.daggerCompiler)
    implementation(libs.retrofit)
    implementation(libs.moshiKotlin)
}
