package io.yahorbarkouski.notion.toggler.core.annotation

/**
 * Annotation that marks a field as being documented for the feature flag generation.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Documented
