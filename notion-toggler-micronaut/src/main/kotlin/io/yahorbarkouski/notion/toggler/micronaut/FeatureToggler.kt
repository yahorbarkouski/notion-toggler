package io.yahorbarkouski.notion.toggler.micronaut

import io.yahorbarkouski.notion.toggler.core.FeatureFlag
import io.yahorbarkouski.notion.toggler.core.fetcher.DefaultFeatureFetcher
import io.yahorbarkouski.notion.toggler.micronaut.config.NotionTogglerProperties
import jakarta.inject.Singleton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import javax.annotation.PostConstruct

/**
 * Service that is responsible for fetching the list of features from the provided [fetcher]
 * and making it available to the rest of the application.
 *
 * @param fetcher the [DefaultFeatureFetcher] that is responsible for fetching the feature toggles from a database.
 * @param properties the [NotionTogglerProperties] containing the configuration for the service.
 */
@Suppress("unused")
@Singleton
class FeatureToggler(
    private val fetcher: DefaultFeatureFetcher<FeatureFlag>,
    private val properties: NotionTogglerProperties
) {

    private val featureToggles = ConcurrentHashMap<String, FeatureFlag>()
    private val featureTogglesAvailability = ConcurrentHashMap<String, Boolean>()

    /**
     * Initializes the service by starting a coroutine that will refresh the feature toggles every $refreshInterval seconds.
     */
    @PostConstruct
    @DelicateCoroutinesApi
    fun start() {
        GlobalScope.launch {
            while (true) {
                fetcher.fetchFeatureFlagAvailability(properties.environment).forEach { (feature, enabled) ->
                    featureTogglesAvailability[feature] = enabled
                }

                val toggles = fetcher.fetchFeatureFlags()
                featureToggles.clear()
                toggles.forEach { toggle -> featureToggles[toggle.name] = toggle }

                delay(properties.refreshInterval * 1000L)
            }
        }
    }

    /**
     * Determines whether the feature with the given [featureName] is enabled.
     *
     * @param featureName the name of the feature to check.
     * @return true if the feature is enabled, false otherwise.
     */
    @Synchronized
    fun isEnabled(featureName: String): Boolean = featureTogglesAvailability[featureName] ?: false

    /**
     * Gets the [FeatureFlag] with the given [featureName].
     *
     * @param featureName the name of the feature to get.
     * @return the [FeatureFlag] with the given name, or null if no such feature exists.
     */
    @Synchronized
    fun get(featureName: String): FeatureFlag? = featureToggles[featureName]

    inline fun <T> ifEnabled(featureName: String, body: () -> T) {
        if (isEnabled(featureName)) {
            body.invoke()
        }
    }
}
