plugins {
    id("java")
    id("maven-publish")
}

java {
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    implementation("com.github.seratch:notion-sdk-jvm-core:1.7.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.22")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.assertj:assertj-core:3.24.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.mockito:mockito-inline:4.11.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            versionMapping {
                allVariants {
                    fromResolutionResult()
                }
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                withXml {
                    val root = asNode()
                    var nodes = root["dependencyManagement"] as groovy.util.NodeList
                    while (nodes.isNotEmpty()) {
                        root.remove(nodes.first() as groovy.util.Node)

                        nodes = root["dependencyManagement"] as groovy.util.NodeList
                    }
                }
                name.set(project.name)
                description.set(project.description)
            }
        }
    }
}
