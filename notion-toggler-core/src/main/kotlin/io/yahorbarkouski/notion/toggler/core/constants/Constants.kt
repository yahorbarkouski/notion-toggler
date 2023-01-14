package io.yahorbarkouski.notion.toggler.core.constants

/**
 * Default notion database name, used when no database name is provided.
 */
const val DEFAULT_DATABASE_NAME = "Features"

/**
 * Base class for Feature Flags, defaults to "io.yahorbarkouski.notion.toggler.core.FeatureFlag",
 * could be overridden if custom FeatureFlag class is needed.
 */
const val BASE_FEATURE_FLAG_CLASS = "io.yahorbarkouski.notion.toggler.core.FeatureFlag"

/**
 * Default refresh interval for feature flags fetch via Notion API, in seconds, defaults to 30.
 * [Notion rate limits](https://developers.notion.com/reference/request-limits)
 */
const val DEFAULT_REFRESH_INTERVAL = 30L

/**
 * Default environment name, used when no environment name is provided.
 */
const val DEFAULT_ENVIRONMENT = "Default"
