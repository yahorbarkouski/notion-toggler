plugins {
    `java-gradle-plugin`
    `maven-publish-conventions`

    id("com.gradle.plugin-publish") version "1.0.0"
    id("org.jmailen.kotlinter") version "3.10.0"
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    api(project(":notion-toggler-core", "default"))
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jboss.forge.roaster:roaster-jdt:2.26.0.Final")
    implementation("com.squareup:kotlinpoet:1.12.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.mockito:mockito-inline:4.8.1")
}

tasks.check {
    dependsOn("installKotlinterPrePushHook")
}

gradlePlugin {
    plugins {
        create("notionToggler") {
            id = "io.yahorbarkouski.notion.toggler"
            implementationClass = "io.yahorbarkouski.notion.toggler.gradle.NotionTogglerPlugin"
            displayName = "Notion Toggler gradle plugin"
            description = "Gradle plugin for Notion Feature Toggles code generation"
            version = project.version as String
        }
    }
}
