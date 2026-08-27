package com.ariastro.portfolio.ui.sections.builds

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

/**
 * Minimal Kotlin syntax highlighter producing an [AnnotatedString].
 *
 * Deliberately a pure function (no @Composable): it is cheap to unit-test and can run off the
 * composition. Kept internal to the builds section since it is an implementation detail of the
 * IDE mock, not a reusable design-system component.
 */

// Hoisted to avoid per-frame allocations.
private val WORD_PATTERN = Regex("[a-zA-Z_][a-zA-Z0-9_]*")
private val KEYWORDS = setOf(
    "package", "import", "object", "val", "fun", "class", "listOf",
    "override", "interface", "enum", "return",
)

internal fun highlightKotlin(
    code: String,
    isDark: Boolean,
    commentColor: Color,
    plainColor: Color,
): AnnotatedString {
    val keywordColor = if (isDark) Color(0xFFF43F5E) else Color(0xFFBE123C)
    val stringColor = if (isDark) Color(0xFF34D399) else Color(0xFF047857)

    return buildAnnotatedString {
        withStyle(SpanStyle(color = plainColor)) {
            val lines = code.lines()
            lines.forEachIndexed { index, line ->
                var i = 0
                while (i < line.length) {
                    // Comment check: the rest of the line is a comment.
                    if (line.startsWith("//", i) ||
                        line.startsWith("/*", i) ||
                        line.startsWith(" *", i) ||
                        line.startsWith(" */", i)
                    ) {
                        withStyle(SpanStyle(color = commentColor)) {
                            append(line.substring(i))
                        }
                        break
                    }
                    // String literal check.
                    if (line[i] == '"') {
                        val end = line.indexOf('"', i + 1)
                        if (end != -1) {
                            withStyle(SpanStyle(color = stringColor)) {
                                append(line.substring(i, end + 1))
                            }
                            i = end + 1
                            continue
                        }
                    }
                    // Identifier check without substring allocations.
                    val wordMatch = WORD_PATTERN.find(line, i)
                    if (wordMatch != null && wordMatch.range.first == i) {
                        val word = wordMatch.value
                        if (word in KEYWORDS) {
                            withStyle(SpanStyle(color = keywordColor, fontWeight = FontWeight.Bold)) {
                                append(word)
                            }
                        } else {
                            append(word)
                        }
                        i += word.length
                    } else {
                        append(line[i])
                        i++
                    }
                }
                if (index < lines.lastIndex) {
                    append("\n")
                }
            }
        }
    }
}
