package io.yahorbarkouski.notion.toggler.spring.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "notion.toggler")
@ConstructorBinding
data class NotionTogglerProperties(
    val token: String? = null,
    val databaseName: String = DEFAULT_DATABASE_NAME,
    val modelPath: String = BASE_FEATURE_FLAG_CLASS,
    val refreshInterval: Long = DEFAULT_REFRESH_INTERVAL
) {

    companion object {

        const val DEFAULT_DATABASE_NAME = "Features"
        const val BASE_FEATURE_FLAG_CLASS = "io.yahorbarkouski.notion.toggler.core.FeatureFlag"
        const val DEFAULT_REFRESH_INTERVAL = 30L
    }
}
