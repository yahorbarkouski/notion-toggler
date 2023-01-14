package io.yahorbarkouski.notion.toggler.gradle.generator

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import io.yahorbarkouski.notion.toggler.core.FeatureFlag
import io.yahorbarkouski.notion.toggler.gradle.util.toEnumStyle
import io.yahorbarkouski.notion.toggler.gradle.util.toFormattedLine
import io.yahorbarkouski.notion.toggler.gradle.util.toMapOfWords
import java.io.File
import java.nio.file.Path

class KotlinFeatureGenerator : FeatureGenerator() {

    /**
     * Generates a Kotlin file with FeatureFlag enum class.
     *
     * @param features the list of features to generate
     * @param packageName the package name for the generated file
     * @param projectDirectory the project directory to generate Features to
     */
    override fun generate(
        features: List<FeatureFlag>,
        packageName: String,
        projectDirectory: File
    ) {
        val sourceSetDirectory = File(projectDirectory, KOTLIN_SOURCE_SET)
        println(
            "Generating features to $sourceSetDirectory/" +
                "$packageName/Features.kt"
        )

        val featureBuilder = TypeSpec.objectBuilder("Features")
            .addKdoc(FILE_HEADER_KDOC)
            .addAnnotation(
                AnnotationSpec.Companion.builder(Suppress::class)
                    .addMember(CodeBlock.of("names = [\"RedundantVisibilityModifier\"]"))
                    .build()
            )

        for (feature in features) {
            val property = PropertySpec.builder(feature.name.toEnumStyle(), String::class, KModifier.CONST)
                .initializer("%S", feature.name)

            println("${feature.achieveDocumentedFields()}!!!!")

            for ((title, text) in feature.achieveDocumentedFields()) {
                property.addKdoc(generateValidKDocText(title, text))
            }

            featureBuilder.addProperty(property.build())
        }

        sourceSetDirectory.mkdirs()
        FileSpec.builder(packageName, "Features")
            .addType(featureBuilder.build())
            .indent("\t")
            .build()
            .writeTo(Path.of("${sourceSetDirectory.path}/"))
    }

    private fun generateValidKDocText(title: String, text: String?): String {
        var kDocText = "__${title}__: "
        kDocText += if (text?.trim()?.isNotBlank() == true) {
            text.toMapOfWords().toFormattedLine()
        } else { EMPTY_VALUE }

        return kDocText
    }

    companion object {
        private const val KOTLIN_SOURCE_SET = "src/main/kotlin/"

        private const val EMPTY_VALUE = "empty"
    }
}
