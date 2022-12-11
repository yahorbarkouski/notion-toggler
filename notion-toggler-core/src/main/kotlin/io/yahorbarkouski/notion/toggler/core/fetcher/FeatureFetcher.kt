package io.yahorbarkouski.notion.toggler.core.fetcher

import io.yahorbarkouski.notion.toggler.core.FeatureFlag
import notion.api.v1.NotionClient
import notion.api.v1.model.pages.Page
import notion.api.v1.request.search.SearchRequest
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties

/**
 * The `FeatureFetcher` class is a generic class that fetches feature flags from a Notion database
 * and converts them to instances of a specified class `T`, which must extend the `FeatureFlag` class.
 *
 * @param notionClient the `NotionClient` instance to use for querying the database.
 * @param databaseName the name of the Notion database to query, should be shared with your brand-new Notion API token.
 * @param klass the `KClass` instance that specifies the class to convert the fetched feature flags to.
 */
class FeatureFetcher<T : FeatureFlag>(
    private val notionClient: NotionClient,
    private val databaseName: String,
    private val klass: KClass<T>
) {

    /**
     * The `databaseId` property is a `String` that stores the ID of the Notion database.
     * This property is lazily initialized using the `databaseName` provided in the constructor
     * to search for the database using the `notionClient`.
     */
    private val databaseId: String by lazy {
        notionClient.search(
            databaseName,
            SearchRequest.SearchFilter("database", property = "object")
        ).results.first().asDatabase().id
    }

    /**
     * Queries the Notion database using the `databaseId`
     * and returns a list of feature flags of type `T` that are converted from the query results
     * using the `convertToFeature()` method.
     */
    fun refreshFeatureToggles(): List<T> {
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
     * @param page The `Page` object to convert to an instance of `T`.
     * @return The converted instance of `T`.
     * @throws IllegalArgumentException if a property in the `klass` instance is not found or
     * if the property type is not supported.
     */
    private fun convertToFeature(page: Page): T {
        val featureToggle = klass.createInstance()
        for (property in page.properties) {
            klass.memberProperties
                .filterIsInstance<KMutableProperty<*>>()
                .firstOrNull { it.name.lowercase() == property.key.lowercase() }
                ?.let {
                    val setter = it.setter
                    when (it.returnType.classifier) {
                        String::class -> {
                            property.value.apply {
                                title?.let { title ->
                                    if (title.isNotEmpty()) {
                                        setter.call(featureToggle, title.first().plainText)
                                    }
                                }
                                richText?.let { richText ->
                                    if (richText.isNotEmpty()) {
                                        setter.call(featureToggle, richText.first().plainText)
                                    }
                                }
                                people?.let { user ->
                                    if (user.isNotEmpty()) {
                                        setter.call(featureToggle, user.first().name)
                                    }
                                }
                            }
                        }
                        Boolean::class -> {
                            setter.call(featureToggle, property.value.checkbox)
                        }
                        Number::class -> {
                            setter.call(featureToggle, property.value.number)
                        }
                        else -> {
                            throw IllegalArgumentException("Unsupported type ${it.returnType.classifier}")
                        }
                    }
                } ?: throw IllegalArgumentException("There is no property mapped to type ${property.key} in ${klass.qualifiedName}")
        }
        return featureToggle
    }
}

fun main() {
    FeatureFetcher(
        NotionClient("secret_Q3a6eehEg6d4wD5GNfDNKBS9qpV0TIwH65xgUemGNvd"),
        "Notion",
        FeatureFlag::class
    ).refreshFeatureToggles()
}
