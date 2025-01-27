import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
    java
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "8.11"
    id("com.diffplug.spotless") version "7.0.2"
    id("com.adarshr.test-logger") version "4.0.0"
}

group = "pl.z-dna"
version = "0.0.1-SNAPSHOT"

spotless {
    java {
        cleanthat()
            .sourceCompatibility("23")
            .version("2.21")
            .addMutator("SafeAndConsensual")
        removeUnusedImports()
        googleJavaFormat("1.25.2").aosp().reorderImports(true)
        formatAnnotations()
    }
}

testlogger {
    theme = ThemeType.MOCHA
    slowThreshold = 5000
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(23)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("com.okta.spring:okta-spring-boot-starter:3.0.7")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.3.RELEASE")
    implementation("com.googlecode.libphonenumber:libphonenumber:8.13.52")
    implementation("jakarta.mail:jakarta.mail-api:2.1.3")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("com.konghq:unirest-java-core:4.4.5")
    implementation("com.konghq:unirest-objectmapper-jackson:4.2.9")
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.assertj:assertj-core:3.27.2")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

val test = tasks.named<Test>("test"){
    useJUnitPlatform{
        excludeTags("extended")
    }
}

val extendedTest = tasks.register<Test>("extendedTest") {
    group = "verification"
    description = "Runs all tests with the 'extended' tag"
    useJUnitPlatform {
        includeTags("extended")
    }
    shouldRunAfter(test)
}

val check by tasks.existing
val fullCheck = tasks.register("fullCheck") {
    group = "verification"
    description = "Runs all verifications, including extended tests and spotless check"
    dependsOn(check, extendedTest)
}

tasks.named("build") {
    dependsOn(fullCheck)
}