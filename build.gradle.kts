import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.17"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.6.20"
    kotlin("plugin.spring") version "1.6.20"
    kotlin("plugin.jpa") version "1.6.20"
    kotlin("kapt") version "1.9.21"
}

group = "ru.konkatenazia"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val telegram = "6.8.0"
val springDocApi = "1.7.0"
val ktReflectVersion = "1.6.20"
val ktStdLib = "1.6.20"
val shedlockVersion = "4.43.0"
val mapstructVersion = "1.5.3.Final"
val apacheCommonsVersion = "1.24.0"
val jaudiotaggerVersion = "2.0.3"
val tukaaniVersion = "1.9"

dependencies {
    implementation("org.tukaani:xz:${tukaaniVersion}")
    implementation("org:jaudiotagger:${jaudiotaggerVersion}")
    implementation("org.apache.commons:commons-compress:${apacheCommonsVersion}")
    implementation("net.javacrumbs.shedlock:shedlock-spring:${shedlockVersion}")
    implementation("net.javacrumbs.shedlock:shedlock-provider-jdbc-template:${shedlockVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${ktStdLib}")
    implementation("org.springdoc:springdoc-openapi-ui:${springDocApi}")
    implementation("org.telegram:telegrambots-spring-boot-starter:${telegram}")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.mapstruct:mapstruct:${mapstructVersion}")
    kapt("org.mapstruct:mapstruct-processor:${mapstructVersion}")
    kaptTest("org.mapstruct:mapstruct-processor:${mapstructVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${ktReflectVersion}")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}
