plugins {
    java
}

group = "xyz.oliwer"
version = "0.0.1-BETA"

repositories {
    mavenCentral()
}

dependencies {
    // JetBrains
    implementation("org.jetbrains:annotations:22.0.0")

    // Google
    implementation("com.google.code.gson:gson:2.8.8")

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}