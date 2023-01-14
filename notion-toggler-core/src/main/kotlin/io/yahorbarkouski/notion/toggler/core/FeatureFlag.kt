package io.yahorbarkouski.notion.toggler.core

/**
 * Base open class for feature flags,
 * that represents a feature flag with a name, an enabled state, and an optional description.
 */
open class FeatureFlag(
    open var name: String = "",
    open var description: String? = "",
    open var created: String? = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FeatureFlag) return false

        if (name != other.name) return false
        if (created != other.created) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (created?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "FeatureToggle(name='$name', description=$description, created=$created)"
    }
}
