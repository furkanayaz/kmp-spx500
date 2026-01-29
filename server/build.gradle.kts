import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
    kotlin("plugin.serialization") version "2.2.0"
    id("com.google.devtools.ksp") version "2.2.0-2.0.2"
}

group = "org.ayaz.bookstore"
version = "1.0.0"
application {
    mainClass.set("org.ayaz.bookstore.ApplicationKt")
    
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.ktor.serverNetty)
    implementation(libs.logback)
    implementation(libs.call.logging)
    implementation(libs.cors)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger)
    implementation(libs.ktor.auth)
    implementation(libs.ktor.auth.jwt)
    implementation(libs.koin.annotations)
    implementation(libs.status.pages)
    implementation(libs.content.negotiation)
    implementation(libs.serialization.json)
    implementation(libs.sessions)
    implementation(libs.kmongo)
    implementation(libs.kmongo.coroutine)
    implementation(libs.kotlinx.datetime)
    implementation(libs.hibernate.validator)
    implementation(libs.jakarta.validation.api)
    implementation(libs.jakarta.el)
    ksp(libs.koin.ksp.compiler)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xannotation-default-target=param-property"))
}