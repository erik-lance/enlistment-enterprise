plugins {
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.unbroken-dome.test-sets") version "4.0.0"
    java
}

group = "com.orangeandbronze.enlistment"
version = "0.0.1-SNAPSHOT"
val testcontainersVersion = "1.16.2"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation( "org.springframework.boot:spring-boot-starter-web")
    implementation( "org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework:spring-aspects")

    implementation("org.apache.commons:commons-lang3")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")
    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
    testImplementation("org.testcontainers:localstack:$testcontainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")


}

testSets {
    libraries {
        create("testCommon")
    }
    create("integrationTest") {
        imports("testCommon")
    }

    val unitTest by getting {
        imports("testCommon")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}