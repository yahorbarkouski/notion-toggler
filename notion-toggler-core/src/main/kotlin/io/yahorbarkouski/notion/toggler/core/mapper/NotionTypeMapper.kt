package io.yahorbarkouski.notion.toggler.core.mapper

import io.yahorbarkouski.notion.toggler.core.fetcher.FeatureFetcher
import notion.api.v1.model.pages.PageProperty
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.isSubclassOf

class NotionTypeMapper<T>(private val featureToggle: T) {

    fun applyStringValue(
        property: KMutableProperty<*>,
        propertyValue: Map.Entry<String, PageProperty>
    ) {
        propertyValue.value.apply {
            title?.let { title ->
                if (title.isNotEmpty()) {
                    property.setter.call(featureToggle, title.first().plainText)
                }
            }
            richText?.let { richText ->
                if (richText.isNotEmpty()) {
                    property.setter.call(featureToggle, richText.first().plainText)
                }
            }
            people?.let { user ->
                if (user.isNotEmpty()) {
                    property.setter.call(featureToggle, user.first().name)
                }
            }
            url?.let { url ->
                property.setter.call(featureToggle, url)
            }
            email?.let { email ->
                property.setter.call(featureToggle, email)
            }
            phoneNumber?.let { phoneNumber ->
                property.setter.call(featureToggle, phoneNumber)
            }
            createdTime?.let { createdTime ->
                property.setter.call(featureToggle, createdTime)
            }
            lastEditedTime?.let { lastEditedTime ->
                property.setter.call(featureToggle, lastEditedTime)
            }
        }
    }

    fun applyBooleanValue(
        property: KMutableProperty<*>,
        propertyValue: Map.Entry<String, PageProperty>
    ) {
        propertyValue.value.apply {
            checkbox?.let { checkbox ->
                property.setter.call(featureToggle, checkbox)
            }
            select?.let { select ->
                property.setter.call(featureToggle, select.name == "Yes")
            }
        }
    }

    fun applyNumberValue(
        property: KMutableProperty<*>,
        propertyValue: Map.Entry<String, PageProperty>
    ) {
        propertyValue.value.apply {
            number?.let { number ->
                property.setter.call(featureToggle, number)
            }
        }
    }

    fun applyEnumValue(
        property: KMutableProperty<*>,
        propertyValue: Map.Entry<String, PageProperty>
    ) {
        val classifier = property.returnType.classifier as KClass<*>
        property.setter.call(
            featureToggle,
            classifier.java
                .getMethod(FeatureFetcher.VALUE_OF, String::class.java)
                .invoke(null, propertyValue.value.select?.name)
        )
    }

    fun applyCollectionValue(it: KMutableProperty<*>, property: Map.Entry<String, PageProperty>) {
        val enumType = it.returnType.arguments[0].type!!.classifier as KClass<*>
        if (enumType.isSubclassOf(Enum::class)) {
            it.setter.call(
                featureToggle,
                property.value.multiSelect?.map { select ->
                    enumType.java
                        .getMethod(FeatureFetcher.VALUE_OF, String::class.java)
                        .invoke(null, select.name)
                }
            )
        } else {
            throw IllegalArgumentException(
                "Collection of objects is not supported. Please use collections of Enums instead."
            )
        }
    }
}
