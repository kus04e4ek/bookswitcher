plugins {
	java
    id("com.google.protobuf") version "0.9.5"
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.culab"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
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
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")

    implementation("com.google.protobuf:protobuf-java:4.30.2")
    implementation("io.grpc:grpc-netty-shaded:1.51.0")
    implementation("io.grpc:grpc-protobuf:1.51.0")
    implementation("io.grpc:grpc-stub:1.51.0")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
	
	compileOnly("org.projectlombok:lombok")

	runtimeOnly("org.postgresql:postgresql")

	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.apache.httpcomponents.client5:httpclient5")
    testImplementation("org.testcontainers:postgresql:1.17.6")
    testImplementation("org.testcontainers:junit-jupiter:1.17.6")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

protobuf {
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.51.0"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
            }
        }
    }
}

tasks.withType<JavaCompile> {
    if (System.getenv("CI_RUNNING") != null) {
        exclude("ru/culab/bookswitcher/controller/**")
        exclude("ru/culab/bookswitcher/convert/**")
        exclude("ru/culab/bookswitcher/repository/**")
        exclude("ru/culab/bookswitcher/service/**")
    }
}

tasks.withType<Test> {
    if (System.getenv("CI_RUNNING") != null) {
        exclude("ru/culab/bookswitcher/controller/**")
        exclude("ru/culab/bookswitcher/convert/**")
        exclude("ru/culab/bookswitcher/repository/**")
        exclude("ru/culab/bookswitcher/service/**")
    }

	useJUnitPlatform()
}
