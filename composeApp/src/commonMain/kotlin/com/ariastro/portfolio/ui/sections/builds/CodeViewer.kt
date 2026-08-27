package com.ariastro.portfolio.ui.sections.builds

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ariastro.portfolio.domain.model.Project
import com.ariastro.portfolio.ui.theme.PortfolioTheme

/** Line numbers + syntax-highlighted pseudo-Kotlin source for the selected project. */
@Composable
internal fun CodeViewer(project: Project) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val scroll = rememberScrollState()

    val codeText = remember(project) { project.toMockSourceCode() }

    val commentColor = if (extra.isDark) Color(0xFF8B93A5) else Color(0xFF6E7485)
    val plainColor = scheme.onSurface
    val highlightedCode = remember(codeText, extra.isDark, commentColor, plainColor) {
        highlightKotlin(
            code = codeText,
            isDark = extra.isDark,
            commentColor = commentColor,
            plainColor = plainColor,
        )
    }

    val lines = codeText.lines()
    // Align the gutter with the code block by sharing the exact line height.
    val codeTextStyle = MaterialTheme.typography.labelLarge.copy(
        fontSize = 12.sp,
        lineHeight = 18.sp,
    )

    Row(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scroll),
    ) {
        // Line numbers
        Column(
            modifier = Modifier.padding(end = 12.dp),
            horizontalAlignment = Alignment.End,
        ) {
            for (i in lines.indices) {
                Text(
                    text = (i + 1).toString(),
                    style = codeTextStyle,
                    color = extra.muted,
                )
            }
        }

        // Code content
        Text(
            text = highlightedCode,
            style = codeTextStyle,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

/** Builds the pseudo-Kotlin source displayed for a project. Pure and trivially testable. */
internal fun Project.toMockSourceCode(): String {
    val stackList = stack.joinToString(", ") { "\"$it\"" }
    val highlightsList = highlights.joinToString("\n            ") { "+ \"$it\"" }
    return """
package portfolio.projects

import id.ariastro.portfolio.data.*

object ${fileName.removeSuffix(".kt")} : Project {
    val role = "$role"
    val category = "$category"
    val year = "$year"
    val status = "$status"

    val blurb = "$blurb"

    val stack = listOf(
        $stackList
    )

    fun getReleaseNotes() = buildString {
        // $story

        highlights {
            $highlightsList
        }
    }
}
    """.trimIndent()
}
