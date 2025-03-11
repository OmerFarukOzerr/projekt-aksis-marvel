buildscript {

    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        val nav_version = "2.8.8"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
        val kotlinVersion = "1.9.22"
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlin("serialization", version = kotlinVersion))


    }
}

plugins {
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.android.application") version "8.1.1" apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false

}