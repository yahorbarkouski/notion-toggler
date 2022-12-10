plugins {
    kotlin("jvm") version "1.7.20" apply false
    kotlin("plugin.spring") version "1.7.20" apply false

    id("io.spring.dependency-management") version "1.0.15.RELEASE" apply false
    id("com.adarshr.test-logger") version "3.2.0" apply false
}

allprojects {
    group = "io.yahorbarkouski"
    version = "1.0.0"
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "com.adarshr.test-logger")
}
