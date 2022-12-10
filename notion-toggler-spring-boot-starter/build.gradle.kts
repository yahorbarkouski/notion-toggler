plugins {
    id("java")
}

java {
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    api(project(":notion-toggler-core", "default"))
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.6.14"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework:spring-test")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.mockito:mockito-inline:4.8.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.8.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
