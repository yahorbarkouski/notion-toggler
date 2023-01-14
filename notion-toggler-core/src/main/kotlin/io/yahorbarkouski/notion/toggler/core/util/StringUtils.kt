package io.yahorbarkouski.notion.toggler.core.util

fun String.camelToWords(): String {
    return fold(StringBuilder(length)) { acc, c ->
        if (c in 'A'..'Z') {
            (if (acc.isNotEmpty()) acc.append(' ') else acc).append(c + ('a' - 'A'))
        } else acc.append(c)
    }.toString()
}
