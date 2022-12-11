# Notion Feature Toggler
> Notion Feature Toggler is a lightweight, customizable, and completely free tool for managing feature flags in your application. It is based on the powerful Notion API, making it easy to manage which features are available in your app.

## Why? 
Why not? All feature toggle providers on the market come with **a cost** and **lack customization** options. They **rely on unreliable "strings"** of names for accessing features, and doesn't really provide any in-code information. Notion Feature Toggler kills these issues, providing a flexible, free, and intuitive way to manage and access feature flags.

## Core features
### Full customization of the FeatureFlag model
You can easily customize the `FeatureFlag` model to fit your needs and use this information in your code. For example, you can add a new property to the Notion Database to let everyone know that a new feature may break production, or add a new Person type column to differentiate Feature Flags by who is responsible for them.

### Completely free
Unlike other feature toggle providers, the Notion Feature Toggler has no cost at all because it uses the Notion API, which has [no limits](https://developers.notion.com/reference/request-limits) on the number of requests per time period. If you're developing a startup and using Notion for documentation, this is the easiest and most cost-effective way to manage your features.

### Spring Boot and Micronaut starters
The Notion Feature Toggler comes with pre-built integrations for Spring Boot and Micronaut, so you don't have to worry about writing your own notion-toggler-client. Just add the dependency and start using it.

### Customizable features refresh-rate
With the Notion Feature Toggler, you have full control over the interval at which the list of features is updated. This allows you to avoid the common problem of not knowing whether a feature is up-to-date or not, and how long to wait for it to be updated. However, please keep in mind the rate limiters of the Notion API, which is 3 seconds.

### Gradle plugin
The standard Notion Feature Toggler SDK relies on strings for accessing features, which can be error-prone and hard to maintain. The Gradle Plugin for generating constants over existing Feature Flags makes the system more robust and easier to maintain, and allows you to view information about your features directly in your code.

## Usage

1. First, you need to create a new integration in Notion to get an Integration API Token:
    - Go to [my-integrations](https://www.notion.so/my-integrations) page;
    - Give your integration a name (we recommend using the "-toggler" postfix to avoid confusion);
    - Choose the workspace where you want to store your Feature Flag database;
    - Give your integration the **Read Content Capability** permission. Notion Toggler only reads your database, no write-operations needed;
    - Click the Submit button and copy your **Internal Integration Token**.
2. Next, you need to create a database and connect it to your integration:
    - You can duplicate the following database in your Notion workspace: https://industrious-wool-c90.notion.site/86969c46aed94f8ebfb667ffef76ba2c?v=98dfa66ffbeb4e5b80877e5bc2362f84;
    - Click the three dots in the top-right corner of the database page, and select "Add Connection". Choose the integration you created in step 1;
    - Optional: If you haven't connected Notion Toggler to your code yet, you can add new properties to your database as needed. For example, you might want to create a new column to store commands for your Feature Toggles.
3. Import Notion Toggler into your project:
    - Spring Boot Starter dependency:
        ```kotlin
        implementation("com.github.notion-toggler:notion-toggler-spring-boot-starter:1.0.0")
        ```
    - Micronaut dependency:
         ```kotlin
        implementation("com.github.notion-toggler:notion-toggler-micronaut:1.0.0")
        ```
    - If you're using a different framework, you can use the core library and implement your own integration:
        ```kotlin
        implementation("com.github.notion-toggler:notion-toggler-core:1.0.0")
        ```
4. Optional: If you created your own custom FF model for your database, you need to duplicate the new properties in your code and inherit from the FeatureFlag base model:
    - TODO: Attach the Kotlin and Notion type mapping table
5. Fill in the application properties:
    - The mandatory properties are:

        ```yaml
        notion.toggler.database-name=#the name of your new-brand database
        notion.toggler.token=#your Notion Integration Token
        ```

    - The optional properties are:

        ```yaml
        notion.toggler.refresh-interval=#value in seconds, must be > 3. Default: 30
        notion.toggler.model-path=#path to your custom FF model, like: io.yahorbarkouski.notion.toggler.spring.CustomFeatureFlag
        ```

6. Inject FeatureToggler where needed and start using it: TODO

## Samples
TODO

## How does it work?
TODO

## Gradle Plugin
TODO

## Contribution
TODO