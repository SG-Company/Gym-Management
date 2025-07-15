import com.codingfeline.buildkonfig.compiler.FieldSpec
import com.codingfeline.buildkonfig.gradle.TargetConfigDsl
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.buildkonfig)
}

// ? ===============================================================================================
// ? gradle.properties - CI/CD script
// ? ===============================================================================================
val supabasePublicKeyProvider: String by lazy { getSecret(key = SupabaseProperties.SUPABASE_PUBLIC_KEY.key) }
val supabaseUrlProvider: String by lazy { getSecret(key = SupabaseProperties.SUPABASE_URL.key) }
// ? ------------------------------

fun getSecret(key: String): String {
    // 1. Try local.properties (if it exists)
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        val props = Properties().apply {
            load(localPropertiesFile.inputStream())
        }
        props.getProperty(key)?.let { return it }
    }
    // 2. Fallback to environment variable (CI/CD)
    return System.getenv(key)
        ?: throw GradleException("Missing property: $key. " +
                "Provide it in local.properties or as an environment variable.")
}
// ? ===============================================================================================

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.androidx.compose)
            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.serialization.json)
            api(libs.koin.core)
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
            implementation(libs.backhandler)
            implementation(libs.navigation)
            implementation(libs.bundles.supabase)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = mPackageName
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = mPackageName
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = mVersionCode.toInt()
        versionName = appVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = libs.versions.javaVersion.get().toJavaVersion()
        targetCompatibility = libs.versions.javaVersion.get().toJavaVersion()
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = packageNameDesktop

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = mPackageName
            packageVersion = desktopVersion
        }
    }
}

val printSuccessMessage by tasks.registering {
    doLast {
        println("✅ Build completed successfully!")
    }
}
tasks.named("build") {
    finalizedBy(printSuccessMessage)
}

buildkonfig {
    packageName = mPackageName
    version = appVersion
    defaultConfigs {
        exposeAppVersion
        exposeSupabaseUrl
        exposeSupabaseToken
    }
}

/**
 * Exposes the application version as a build config field.
 * This allows the application version to be accessed at runtime.
 */
val TargetConfigDsl.exposeAppVersion: Unit
    get() = buildConfigField(
        type = FieldSpec.Type.STRING,
        name = "appVersion",
        value = appVersion
    )

/**
 * Exposes the Supabase URL as a build config field.
 * This makes the Supabase URL available in the application code at runtime.
 */
val TargetConfigDsl.exposeSupabaseUrl: Unit
    get() = buildConfigField(
        type = FieldSpec.Type.STRING,
        name = "supabaseUrl",
        value = supabaseUrlProvider
    )

/**
 * Exposes the Supabase token as a build config field.
 * This makes the token accessible in the application code.
 */
val TargetConfigDsl.exposeSupabaseToken: Unit
    get() = buildConfigField(
        type = FieldSpec.Type.STRING,
        name = "supabaseToken",
        value = supabasePublicKeyProvider
    )