package io.yahorbarkouski.notion.toggler.spring.config

import io.yahorbarkouski.notion.toggler.core.FeatureFlag
import io.yahorbarkouski.notion.toggler.core.fetcher.DefaultFeatureFetcher
import notion.api.v1.NotionClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.reflect.KClass

@Suppress("SpringFacetCodeInspection")
@Configuration
@EnableConfigurationProperties(NotionTogglerProperties::class)
@ConditionalOnProperty(prefix = "notion.toggler", name = ["token"])
class NotionTogglerConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun notionClient(properties: NotionTogglerProperties): NotionClient = NotionClient(
        properties.token
    )

    @Suppress("UNCHECKED_CAST")
    @Bean
    @ConditionalOnMissingBean
    fun featureToggleFetcher(notionClient: NotionClient, properties: NotionTogglerProperties) = DefaultFeatureFetcher(
        notionClient,
        properties.databaseName,
        Class.forName(properties.modelPath).kotlin as KClass<FeatureFlag>
    )
}
