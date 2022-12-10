plugins {
    id("java")
}

java {
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    implementation("com.github.seratch:notion-sdk-jvm-core:1.7.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.22")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.mockito:mockito-inline:4.8.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
