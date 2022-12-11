package io.yahorbarkouski.notion.toggler.core.fetcher

import io.yahorbarkouski.notion.toggler.core.FeatureFlag
import io.yahorbarkouski.notion.toggler.core.fetcher.FeatureFetcher.Companion.SEARCH_FILTER_DATABASE
import io.yahorbarkouski.notion.toggler.core.fetcher.FeatureFetcher.Companion.SEARCH_FILTER_OBJECT
import io.yahorbarkouski.notion.toggler.core.mapper.NotionTypeMapper
import notion.api.v1.NotionClient
import notion.api.v1.model.pages.Page
import notion.api.v1.request.search.SearchRequest
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties

/**
 * Default implementation of the `FeatureFetcher` interface.
 *
 * @param notionClient the `NotionClient` instance to use for querying the database.
 * @param databaseName the name of the Notion database to query, should be shared with your brand-new Notion API token.
 * @param klass the `KClass` instance that specifies the class to convert the fetched feature flags to.
 */
class DefaultFeatureFetcher<T : FeatureFlag>(
    private val notionClient: NotionClient,
    private val databaseName: String,
    private val klass: KClass<T>
) : FeatureFetcher<T> {

    /**
     * The `databaseId` property is a `String` that stores the ID of the Notion database.
     * This property is lazily initialized using the `databaseName` provided in the constructor
     * to search for the database using the `notionClient`.
     */
    private val databaseId: String by lazy {
        notionClient.search(
            databaseName,
            SearchRequest.SearchFilter(SEARCH_FILTER_DATABASE, property = SEARCH_FILTER_OBJECT)
        ).results.first().asDatabase().id
    }

    /**
     * Queries the Notion database using the `databaseId`
     * and returns a list of feature flags of type `T` that are converted from the query results
     * using the `convertToFeature()` method.
     */
    override fun fetchFeatureFlags(): List<T> {
        return notionClient.queryDatabase(databaseId).results
            .map { page -> convertToFeature(page) }
            .toList()
    }

    /**
     * This method takes a `Page` object as a parameter and converts it
     * to an instance of the class specified by the `klass` parameter in the constructor.
     * The method iterates over the page properties and uses the property key to match
     * with a property in the `klass` instance. The property value is then set using
     * the property setter, and the resulting object is returned. If a property in the `klass`
     * instance is not found or if the property type is not supported, an `IllegalArgumentException`
     * is thrown.
     *
     * @param page The `Page` notion object to convert to an instance of `T`.
     * @return The converted instance of `T`.
     * @throws IllegalArgumentException if a property in the `klass` instance is not found or
     * if the property type is not supported.
     */
    private fun convertToFeature(page: Page): T {
        val featureToggle = klass.createInstance()
        val mapper = NotionTypeMapper(featureToggle)
        for (property in page.properties) {
            klass.memberProperties
                .filterIsInstance<KMutableProperty<*>>()
                .firstOrNull { it.name.lowercase() == property.key.lowercase() }
                ?.let { featureProperty -> mapper.applyValue(featureProperty, property) }
                ?: throw IllegalArgumentException(
                    "There is no property mapped to type ${property.key} in ${klass.qualifiedName}"
                )
        }
        return featureToggle
    }
}
