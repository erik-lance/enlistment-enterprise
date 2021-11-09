plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.12.0")

    // Use JUnit test framework
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    //testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

}

tasks.test {
    useJUnitPlatform()
    testLogging {
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}
