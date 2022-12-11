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
TODO 

## Samples
TODO

## How does it work?
TODO

## Gradle Plugin
TODO

## Contribution
TODO