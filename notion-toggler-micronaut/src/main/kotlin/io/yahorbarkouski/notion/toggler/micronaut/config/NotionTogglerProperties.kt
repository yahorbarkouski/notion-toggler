package io.yahorbarkouski.notion.toggler.micronaut.config

import io.micronaut.context.annotation.ConfigurationProperties
import io.yahorbarkouski.notion.toggler.core.constants.BASE_FEATURE_FLAG_CLASS
import io.yahorbarkouski.notion.toggler.core.constants.DEFAULT_DATABASE_NAME
import io.yahorbarkouski.notion.toggler.core.constants.DEFAULT_ENVIRONMENT
import io.yahorbarkouski.notion.toggler.core.constants.DEFAULT_REFRESH_INTERVAL

@Suppress("MnInjectionPoints")
@ConfigurationProperties("notion.toggler")
data class NotionTogglerProperties(
    val token: String? = null,
    val databaseName: String = DEFAULT_DATABASE_NAME,
    val modelPath: String = BASE_FEATURE_FLAG_CLASS,
    val refreshInterval: Long = DEFAULT_REFRESH_INTERVAL,
    val environment: String = DEFAULT_ENVIRONMENT
)
