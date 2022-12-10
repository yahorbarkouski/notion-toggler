package io.yahorbarkouski.notion.toggler.gradle

import io.yahorbarkouski.notion.toggler.gradle.extension.NotionExtension
import io.yahorbarkouski.notion.toggler.gradle.task.NotionFeatureToggleCodegenTask
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Gradle plugin for generating feature constants from Notion.
 */
@Suppress("unused")
class NotionTogglerPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        extensions.create("notionToggler", NotionExtension::class.java)
        tasks.register("generateFeatures", NotionFeatureToggleCodegenTask::class.java)
    }
}
