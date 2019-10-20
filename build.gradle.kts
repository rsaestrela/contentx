check(JavaVersion.current().isJava8Compatible) { "Must be built with Java 8 or higher" }

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.50"
}

repositories {
    jcenter()
    maven("https://dl.bintray.com/kotlin/kotlinx")
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}