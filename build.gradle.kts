plugins {
    java
}

repositories {
    mavenCentral()
}

val junitVersion = "5.8.1"

dependencies {
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.12.0")

    // Use JUnit test framework
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")



}

tasks.test {
    useJUnitPlatform()
    testLogging {
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}
