package io.yahorbarkouski.notion.toggler.gradle.task

import io.yahorbarkouski.notion.toggler.core.FeatureFlag
import io.yahorbarkouski.notion.toggler.core.fetcher.FeatureFetcher
import io.yahorbarkouski.notion.toggler.gradle.extension.NotionExtension
import io.yahorbarkouski.notion.toggler.gradle.generator.KotlinFeatureGenerator
import notion.api.v1.NotionClient
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject
import kotlin.reflect.KClass

@CacheableTask
abstract class NotionFeatureToggleCodegenTask @Inject constructor() : DefaultTask() {

    private val featureGenerator = KotlinFeatureGenerator()

    @TaskAction
    fun run() {
        val extension = project.extensions.getByType(NotionExtension::class.java)
        require(extension.token?.isNotBlank() == true) { "Notion token must be set" }

        @Suppress("UNCHECKED_CAST")
        val fetcher = FeatureFetcher(
            NotionClient(extension.token!!),
            extension.databaseName,
            Class.forName(extension.modelPath).kotlin as KClass<FeatureFlag>
        )
        val features = fetcher.refreshFeatureToggles()

        try {
            featureGenerator.generate(
                features,
                extension.packageName,
                project.projectDir
            )
        } catch (e: Exception) {
            System.err.println("Unable to generate features from Notion: ${e.message}")
            e.printStackTrace()
        }
    }

    override fun getDescription(): String {
        return "Generates feature constants from Notion"
    }
}
