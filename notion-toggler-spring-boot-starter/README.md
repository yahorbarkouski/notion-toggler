# Notion Toggler Spring Boot Starter

> This project is a Spring Boot starter that allows you to use Notion Toggler in your Spring Boot application. 
> Notion Toggler is a library that enables you to store and manage feature flags in a Notion database, providing an easy and flexible way to manage feature toggles in your application.

## Usage

To use this starter in your Spring Boot project, add the following dependency to your `build.gradle.kts` file:

```kotlin
implementation("io.yahorbarkouski.notion.toggler:notion-toggler-spring-boot-starter:1.0.0")
```
To configure the Notion Toggler Spring Boot starter, you will need to provide your Notion Toggler API token and database name in your `application.yml` file:
```yaml
notion.toggler:
  token: <your-notion-toggler-api-token>
  database-name: <your-notion-toggler-database-name>
```

You can use the FeatureToggler class in your application to check if a feature is enabled or not. For example:

```kotlin
@Autowired
lateinit var featureToggler: FeatureToggler

fun myMethod() {
    if (featureToggler.isEnabled("my-feature-flag")) {
        // do something
    }
}
```

## Configuration

The following configuration options are available for the Notion Toggler Micronaut starter:

| Property                        | 	Description                                                             | 	Default Value                                      |
|---------------------------------|--------------------------------------------------------------------------|-----------------------------------------------------|
| notion.toggler.token	           | The Notion Toggler API token.                                            | 	null                                               |
| notion.toggler.database-name	   | The name of the Notion database containing the feature flags.	           | "Features"                                          |
| notion.toggler.model-path       | 	The fully-qualified class name of the feature flag model class.	        | "io.yahorbarkouski.notion.toggler.core.FeatureFlag" |
| notion.toggler.refresh-interval | 	The interval in seconds at which the feature flags should be refreshed. | 	30                                                 |
