package io.yahorbarkouski.notion.toggler.gradle.extension

abstract class NotionExtension {

    /**
     * Notion auth token.
     */
    var token: String? = null

    /**
     * Package name for generated files.
     */
    var packageName: String = ""

    /**
     * Database name with Features inside.
     */
    var databaseName: String = DEFAULT_DATABASE_NAME

    /**
     * Path to your custom Feature model, optional
     */
    var modelPath: String = BASE_FEATURE_FLAG_CLASS

    companion object {

        const val DEFAULT_DATABASE_NAME = "Features"
        const val BASE_FEATURE_FLAG_CLASS = "io.yahorbarkouski.notion.toggler.core.FeatureFlag"
    }
}
