package io.yahorbarkouski.notion.toggler.spring

import io.yahorbarkouski.notion.toggler.core.FeatureFlag
import io.yahorbarkouski.notion.toggler.core.fetcher.DefaultFeatureFetcher
import io.yahorbarkouski.notion.toggler.core.fetcher.FeatureFetcher
import io.yahorbarkouski.notion.toggler.spring.config.NotionTogglerProperties
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

/**
 * Service that is responsible for fetching the list of features from the provided [fetcher]
 * and making it available to the rest of the application.
 *
 * @param fetcher the [DefaultFeatureFetcher] that is responsible for fetching the feature toggles from a database.
 * @param properties the [NotionTogglerProperties] containing the configuration for the service.
 */
@Suppress("unused")
@Service
class FeatureToggler(
    private val fetcher: FeatureFetcher<FeatureFlag>,
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
    fun isFeatureEnabled(featureName: String): Boolean {
        return featureTogglesAvailability[featureName] ?: false
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

    companion object {
        private val log: Logger = LoggerFactory.getLogger(FeatureToggler::class.java)
    }
}
