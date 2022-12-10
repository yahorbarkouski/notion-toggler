package io.yahorbarkouski.notion.toggler.spring

import io.yahorbarkouski.notion.toggler.core.FeatureFlag
import io.yahorbarkouski.notion.toggler.core.fetcher.FeatureFetcher
import io.yahorbarkouski.notion.toggler.spring.config.NotionTogglerProperties
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import javax.annotation.PostConstruct

/**
 * Service that is responsible for fetching the list of features from the provided [fetcher]
 * and making it available to the rest of the application.
 *
 * @param fetcher the [FeatureFetcher] that is responsible for fetching the feature toggles from a database.
 * @param properties the [NotionTogglerProperties] containing the configuration for the service.
 */
@Service
class FeatureToggler(
    private val fetcher: FeatureFetcher<FeatureFlag>,
    private val properties: NotionTogglerProperties
) {

    private val featureToggles = ConcurrentHashMap<String, FeatureFlag>()

    /**
     * Initializes the service by starting a coroutine that will refresh the feature toggles every $refreshInterval seconds.
     */
    @PostConstruct
    @DelicateCoroutinesApi
    fun start() {
        GlobalScope.launch {
            while (true) {
                val toggles = fetcher.refreshFeatureToggles()
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
    fun isFeatureEnabled(featureName: String): Boolean {
        return featureToggles[featureName]?.enabled ?: false
    }

    /**
     * Gets the [FeatureFlag] with the given [featureName].
     *
     * @param featureName the name of the feature to get.
     * @return the [FeatureFlag] with the given name, or null if no such feature exists.
     */
    @Synchronized
    fun getFeatureToggle(featureName: String): FeatureFlag? {
        return featureToggles[featureName]
    }
}
