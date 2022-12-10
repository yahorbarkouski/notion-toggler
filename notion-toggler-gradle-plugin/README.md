# Notion Toggler Gradle Plugin
> Gradle plugin for Notion Feature Toggles code generation


## Reason
1. Increase the reliability of feature toggles, the default Notion Toggler SDK rely on strings to identify the feature toggles, which is error-prone and hard to maintain.
2. See description, tags, and other meta information directly in the code, which improves the readability of feature toggles in general.
3. It's hard to maintain stale features once using pure Strings, this plugin will help to find out the stale features and remove them from the codebase.

## Notes
1. This plugin is not a replacement for the Notion Toggler SDK, it is a code generation tool that generates the Notion SDK code for you.
2. This plugin generates git-tracked sources, so the generation doesn't block developers that does not create new notion features and doesn't want to export their Notion Tokens.
3. It's suggested to use notion-toggler together with ktlint gradle plugins (like [kotlinter](https://github.com/jeremymailen/kotlinter-gradle) or [ktlint-gradle](https://github.com/JLLeitschuh/ktlint-gradle)), to keep the generated code clean and readable. Example configuration:
    ```kotlin
   tasks.named("generateFeatures") {
       finalizedBy("formatKotlin")
   }
   ```

## Installation
You need to add the following lines to your `build.gradle.kts` file:
```kotlin
plugins { 
  id("io.yahorbarkouski.notion.toggler") version "<latest version from the Gradle plugin portal>"
}
```

## Gradle Task
### Usage
```bash
gradle generateFeatures
```
### Plugin Extension properties
| Property       | Default           | Type          | Description                                                               | Required |
|----------------|-------------------|---------------|---------------------------------------------------------------------------|---------|
| `token`        | `null`            | String        | Notion API Client Token | *       |
| `packageName`  |               | String?       | Package name for the generated files                                      |         |
| `databaseName` | `null`            | String        | Notion database name                                                      | *       |
| `modelPath`    | `null`            | String        | Fully-qualified class name of the feature flag model class                | *       |


### Description
This task will generate a `$fileName.kt` file in the `$packageName` directory of your project. This file will contain all the feature toggles defined in your Notion database.

### Extension example
```kotlin
notionToggler { 
   token = System.getenv("NOTION_TOKEN")
   packageName = "io.yahorbarkouski.chat.notion.constants"
   databaseName = "Features"
   modelPath = "io.yahorbarkouski.chat.notion.model.FeatureFlag"
}
```
### Generated Features example
TODO

## Building from source
You don’t need to build from source to use notion-toggler, but if you want to try out the latest, notion-toggler can be built and published to your local Maven cache using the Gradle wrapper.

You also need following to:
- Kotlin 1.6.21 or higher
- Gradle 7.x or higher 

```bash
./gradlew publishToMavenLocal
```

## License
Notion toggler gradle plugin is released under version 2.0 of the Apache License.



