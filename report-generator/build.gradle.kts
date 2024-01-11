plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.parcel"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.minio:minio:8.5.7")
    implementation("io.github.openfeign:feign-core:13.1")
    implementation("io.github.openfeign:feign-gson:13.1")
    implementation("org.apache.pdfbox:pdfbox:3.0.1")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:minio:1.19.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}