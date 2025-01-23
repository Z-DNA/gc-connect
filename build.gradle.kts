plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.freefair.lombok") version "8.11"
}

group = "pl.z-dna"
version = "0.0.1-SNAPSHOT"

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
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.3.RELEASE")
	implementation("com.okta.spring:okta-spring-boot-starter:3.0.7")
	implementation("com.googlecode.libphonenumber:libphonenumber:8.13.52")
	implementation("jakarta.mail:jakarta.mail-api:2.1.3")
	implementation("com.auth0:mvc-auth-commons:1.11.0")
	implementation("org.apache.commons:commons-lang3:3.17.0")
	implementation("com.konghq:unirest-java-core:4.4.5")
	implementation("com.konghq:unirest-objectmapper-jackson:4.2.9")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito:mockito-core:5.15.2")
	testImplementation("org.assertj:assertj-core:3.27.2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
