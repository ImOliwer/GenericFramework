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
    implementation("org.jetbrains:annotations:20.1.0")

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks.test {
    useJUnitPlatform()
}