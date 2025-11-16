plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.swagger.core.v3.swagger-gradle-plugin") version "2.2.30"
    kotlin("plugin.jpa") version "1.9.25"
}
val springCloudVersion by extra("2024.0.1")

group = "mk.ukim.finki"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.axonframework:axon-spring-boot-starter:4.11.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
    implementation("io.cloudevents:cloudevents-api:2.4.0")
    implementation("io.cloudevents:cloudevents-core:2.4.0")
    implementation("io.cloudevents:cloudevents-json-jackson:2.4.0")
    implementation("io.cloudevents:cloudevents-kafka:2.4.0")
    implementation("org.springframework.cloud:spring-cloud-starter-consul-all:4.3.0")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.3.0")
    implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.5.0")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("com.h2database:h2:2.2.224")
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.0")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.axonframework:axon-test:4.7.6")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    testImplementation("io.rest-assured:rest-assured:5.3.2")
    testImplementation("io.rest-assured:json-path:5.3.2")
    testImplementation("io.rest-assured:xml-path:5.3.2")
    testImplementation("io.rest-assured:kotlin-extensions:5.3.2")

    testImplementation("io.rest-assured:json-schema-validator:5.3.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs = listOf(
        "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005"
    )
    forkEvery = 0
    maxParallelForks = 1
}
