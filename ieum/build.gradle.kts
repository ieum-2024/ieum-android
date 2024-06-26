// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript{
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.java.net/content/groups/public/")
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.3.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.47")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.8.10")
    }
}
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("com.android.library") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
}