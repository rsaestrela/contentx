plugins {
    id("me.drmaas.ratpack-kotlin") version "1.9.3"
    id("org.jetbrains.kotlin.jvm")
}

repositories {
    jcenter()
    maven("https://dl.bintray.com/kotlin/kotlinx")
}

dependencies {
    implementation(project(":cx-core"))
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.reactivex.rxjava2:rxkotlin:2.4.0")
    implementation("io.ratpack:ratpack-rx2:1.7.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}
