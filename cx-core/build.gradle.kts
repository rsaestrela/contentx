plugins {
    id("org.jetbrains.kotlin.jvm")
}

repositories {
    jcenter()
    maven("https://dl.bintray.com/kotlin/kotlinx")
}

dependencies {
    //compile("org.mongodb:mongodb-driver-reactivestreams:1.12.0")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.2")
    implementation("io.reactivex.rxjava2:rxkotlin:2.4.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}