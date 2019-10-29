plugins {
    id("org.jetbrains.kotlin.jvm")
}

repositories {
    jcenter()
    maven("https://dl.bintray.com/kotlin/kotlinx")
}

dependencies {
    implementation("org.mongodb:mongodb-driver-reactivestreams:1.12.0")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.reactivex.rxjava2:rxkotlin:2.4.0")
    implementation("com.google.flogger:flogger:0.4")
    implementation("com.google.flogger:flogger-system-backend:0.4")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}