package io.yahorbarkouski.notion.toggler.micronaut.config

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.yahorbarkouski.notion.toggler.core.FeatureFlag
import io.yahorbarkouski.notion.toggler.core.fetcher.DefaultFeatureFetcher
import notion.api.v1.NotionClient
import kotlin.reflect.KClass

@Factory
class NotionTogglerFactory {

    @Bean
    fun notionClient(properties: NotionTogglerProperties): NotionClient {
        return NotionClient(properties.token)
    }

    @Suppress("UNCHECKED_CAST")
    @Bean
    fun featureToggleFetcher(notionClient: NotionClient, properties: NotionTogglerProperties) = DefaultFeatureFetcher(
        notionClient,
        properties.databaseName,
        Class.forName(properties.modelPath).kotlin as KClass<FeatureFlag>
    )
}
