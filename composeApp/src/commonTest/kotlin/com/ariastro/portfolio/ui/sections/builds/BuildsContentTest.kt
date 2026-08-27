package com.ariastro.portfolio.ui.sections.builds

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.ariastro.portfolio.domain.model.Brand
import com.ariastro.portfolio.domain.model.LinkType
import com.ariastro.portfolio.domain.model.Project
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * These tests only exist because the logic was extracted from the composable file into pure
 * functions: [highlightKotlin] and [Project.toMockSourceCode].
 */
class BuildsContentTest {

    // region highlightKotlin

    @Test
    fun keywordsAreHighlightedBold() {
        val result = highlightKotlin(
            code = "val x = 1",
            isDark = true,
            commentColor = Color.Gray,
            plainColor = Color.White,
        )

        val keywordRange = result.spanStyles.firstOrNull {
            result.text.substring(it.start, it.end) == "val"
        }
        assertNotNull(keywordRange, "expected a span for the 'val' keyword")
        assertEquals(FontWeight.Bold, keywordRange.item.fontWeight)
    }

    @Test
    fun plainIdentifiersAreNotBold() {
        val result = highlightKotlin(
            code = "val counter = 1",
            isDark = true,
            commentColor = Color.Gray,
            plainColor = Color.White,
        )

        val identifierRange = result.spanStyles.firstOrNull {
            result.text.substring(it.start, it.end) == "counter"
        }
        // Identifiers inherit the outer plain style: no dedicated bold span.
        assertTrue(identifierRange == null || identifierRange.item.fontWeight != FontWeight.Bold)
    }

    @Test
    fun stringLiteralsGetTheirOwnColor() {
        val result = highlightKotlin(
            code = "val s = \"hello\"",
            isDark = true,
            commentColor = Color.Gray,
            plainColor = Color.White,
        )

        val stringRange = result.spanStyles.firstOrNull {
            result.text.substring(it.start, it.end) == "\"hello\""
        }
        assertNotNull(stringRange, "expected a span for the string literal")
        assertNotEquals(Color.White, stringRange.item.color)
    }

    @Test
    fun commentsUseTheGivenCommentColor() {
        val commentColor = Color(0xFF123456)
        val result = highlightKotlin(
            code = "x = 1 // trailing note",
            isDark = true,
            commentColor = commentColor,
            plainColor = Color.White,
        )

        val commentRange = result.spanStyles.firstOrNull {
            result.text.substring(it.start, it.end) == "// trailing note"
        }
        assertNotNull(commentRange, "expected a span for the comment")
        assertEquals(commentColor, commentRange.item.color)
    }

    @Test
    fun outputTextMatchesInput() {
        val code = "package demo\n\nval x = \"a\" // done"
        val result = highlightKotlin(
            code = code,
            isDark = false,
            commentColor = Color.Gray,
            plainColor = Color.Black,
        )
        assertEquals(code, result.text)
    }

    // endregion

    // region Project.toMockSourceCode

    @Test
    fun mockSourceContainsProjectFacts() {
        val code = sampleProject().toMockSourceCode()

        assertTrue(code.contains("object AlphaApp : Project"))
        assertTrue(code.contains("val role = \"Engineer\""))
        assertTrue(code.contains("val category = \"Test\""))
        assertTrue(code.contains("val year = \"2024\""))
        assertTrue(code.contains("\"Kotlin\""))
        assertTrue(code.contains("+ \"highlight one\""))
    }

    // endregion

    private fun sampleProject(): Project = Project(
        id = "alpha",
        index = "01",
        title = "Alpha App",
        role = "Engineer",
        blurb = "blurb",
        story = "story",
        highlights = listOf("highlight one"),
        year = "2024",
        category = "Test",
        link = null,
        linkType = LinkType.NONE,
        stack = listOf("Kotlin"),
        brand = Brand.MY_XL,
        screenshotIds = emptyList(),
    )
}
