import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
    kotlin("plugin.serialization") version "2.3.0"
    id("com.google.devtools.ksp") version "2.2.0-2.0.2"
}

group = "org.ayaz.exchange"
version = "1.0.0"
application {
    mainClass.set("org.ayaz.exchange.ApplicationKt")
    
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.ktor.serverNetty)
    implementation(libs.logback)
    implementation(libs.cors)
    implementation(libs.ktor.auth)
    implementation(libs.ktor.auth.jwt)
    implementation(libs.status.pages)
    implementation(libs.i18n)
    implementation(libs.content.negotiation)
    implementation(libs.serialization.json)
    implementation(libs.sessions)
    implementation(libs.kotlinx.datetime)
    implementation(libs.hibernate.validator)
    implementation(libs.jakarta.validation.api)
    implementation(libs.jakarta.el)
    implementation(libs.ktor.server.core)
    implementation(libs.jedis)
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.mongodb)
    implementation(libs.bundles.koin)
    ksp(libs.koin.ksp.compiler)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xannotation-default-target=param-property"))
}