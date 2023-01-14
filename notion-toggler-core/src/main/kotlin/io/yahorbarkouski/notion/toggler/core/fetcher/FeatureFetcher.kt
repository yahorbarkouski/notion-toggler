package io.yahorbarkouski.notion.toggler.core.fetcher

import io.yahorbarkouski.notion.toggler.core.FeatureFlag

/**
 * The `FeatureFetcher` is a generic interface that fetches feature flags from a Notion database
 * and converts them to instances of a specified class `T`, which must extend the `FeatureFlag` class.
 */
interface FeatureFetcher<T : FeatureFlag> {

    /**
     * Fetches feature flags from the database.
     */
    fun fetchFeatureFlags(): List<T>

    /**
     * Fetches feature flag names with enabled property per environment.
     */
    fun fetchFeatureFlagAvailability(environment: String): Map<String, Boolean>

    companion object {

        const val SEARCH_FILTER_DATABASE = "database"
        const val SEARCH_FILTER_OBJECT = "object"
    }
}
