import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleServicesPlugin)
}

android {

    namespace = "com.example.project2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.project2"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_16
        targetCompatibility = JavaVersion.VERSION_16
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "16"
    }
    packagingOptions {
        resources {
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/DEPENDENCIES.txt"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/license.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/notice.txt"
            excludes += "META-INF/ASL2.0"
            excludes += "META-INF/native-image/org.mongodb/bson/native-image.properties"
        }
    }
}

dependencies {
    implementation(libs.google.services)
    implementation(libs.firebase.core)
    implementation(libs.transport.runtime)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")

    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.1")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

