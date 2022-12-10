apply(from = File(settingsDir, "gradle/repositoriesSettings.gradle.kts"))

rootProject.name = "notion-toggler"

include(
    "notion-toggler-core",
    "notion-toggler-spring-boot-starter"
)
