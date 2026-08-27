package com.ariastro.portfolio.presentation

import com.ariastro.portfolio.domain.model.Brand
import com.ariastro.portfolio.domain.model.Fact
import com.ariastro.portfolio.domain.model.LinkType
import com.ariastro.portfolio.domain.model.Profile
import com.ariastro.portfolio.domain.model.ProfileLink
import com.ariastro.portfolio.domain.model.Project
import com.ariastro.portfolio.domain.model.Section
import com.ariastro.portfolio.domain.repository.PortfolioRepository
import com.ariastro.portfolio.domain.usecase.GetProfileUseCase
import com.ariastro.portfolio.domain.usecase.GetProjectsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * The store is tested against a fake repository: proof that presentation only depends
 * on domain abstractions (Clean Architecture) and that state changes are driven
 * exclusively by intents (MVI).
 */
class PortfolioStoreTest {

    private fun createStore(): PortfolioStore {
        val repository: PortfolioRepository = FakePortfolioRepository()
        return PortfolioStore(
            getProfile = GetProfileUseCase(repository),
            getProjects = GetProjectsUseCase(repository),
        )
    }

    @Test
    fun initialStateLoadsContentFromDomain() {
        val state = createStore().state.value

        assertEquals("Test User", state.profile.displayName)
        assertEquals(2, state.projects.size)
        assertEquals(true, state.isDark)
        assertEquals(0, state.selectedProjectIndex)
        assertEquals(EditorMode.SPLIT, state.editorMode)
        assertNull(state.activeSection)
        assertEquals(0f, state.scrollProgress)
        assertEquals(state.projects.first(), state.selectedProject)
    }

    @Test
    fun toggleAllExperienceFlipsShowAllExperience() {
        val store = createStore()

        assertEquals(false, store.state.value.showAllExperience)

        store.dispatch(PortfolioIntent.ToggleAllExperience)
        assertEquals(true, store.state.value.showAllExperience)

        store.dispatch(PortfolioIntent.ToggleAllExperience)
        assertEquals(false, store.state.value.showAllExperience)
    }

    @Test
    fun toggleThemeFlipsIsDark() {
        val store = createStore()

        store.dispatch(PortfolioIntent.ToggleTheme)
        assertEquals(false, store.state.value.isDark)

        store.dispatch(PortfolioIntent.ToggleTheme)
        assertEquals(true, store.state.value.isDark)
    }

    @Test
    fun selectProjectUpdatesSelection() {
        val store = createStore()

        store.dispatch(PortfolioIntent.SelectProject(index = 1))

        assertEquals(1, store.state.value.selectedProjectIndex)
        assertEquals("Beta", store.state.value.selectedProject?.title)
    }

    @Test
    fun selectProjectIgnoresOutOfRangeIndex() {
        val store = createStore()

        store.dispatch(PortfolioIntent.SelectProject(index = 5))
        store.dispatch(PortfolioIntent.SelectProject(index = -1))

        assertEquals(0, store.state.value.selectedProjectIndex)
    }

    @Test
    fun changeEditorModeUpdatesState() {
        val store = createStore()

        store.dispatch(PortfolioIntent.ChangeEditorMode(EditorMode.DESIGN))

        assertEquals(EditorMode.DESIGN, store.state.value.editorMode)
    }

    @Test
    fun viewportChangedUpdatesScrollProgress() {
        val store = createStore()

        store.dispatch(PortfolioIntent.ViewportChanged(position = 250, max = 1000))

        assertEquals(0.25f, store.state.value.scrollProgress)
    }

    @Test
    fun viewportChangedWithoutMaxKeepsProgressZero() {
        val store = createStore()

        store.dispatch(PortfolioIntent.ViewportChanged(position = 100, max = 0))

        assertEquals(0f, store.state.value.scrollProgress)
    }

    @Test
    fun activeSectionFollowsScrollAndPositions() {
        val store = createStore()
        store.dispatch(PortfolioIntent.SectionPositionChanged(Section.README, y = 200))
        store.dispatch(PortfolioIntent.SectionPositionChanged(Section.BUILDS, y = 800))
        store.dispatch(PortfolioIntent.SectionPositionChanged(Section.CONNECT, y = 1600))

        store.dispatch(PortfolioIntent.ViewportChanged(position = 300, max = 2000))
        assertEquals(Section.README, store.state.value.activeSection)

        store.dispatch(PortfolioIntent.ViewportChanged(position = 900, max = 2000))
        assertEquals(Section.BUILDS, store.state.value.activeSection)
    }

    @Test
    fun activeSectionSnapsToConnectAtBottom() {
        val store = createStore()
        store.dispatch(PortfolioIntent.SectionPositionChanged(Section.BUILDS, y = 800))
        store.dispatch(PortfolioIntent.SectionPositionChanged(Section.CONNECT, y = 1600))

        store.dispatch(PortfolioIntent.ViewportChanged(position = 1995, max = 2000))

        assertEquals(Section.CONNECT, store.state.value.activeSection)
    }

    @Test
    fun navigateToSectionEmitsScrollToEffectWithOffset() = runTest {
        val store = createStore()
        store.dispatch(PortfolioIntent.SectionPositionChanged(Section.BUILDS, y = 800))

        store.dispatch(PortfolioIntent.NavigateToSection(Section.BUILDS))

        val effect = store.effects.first()
        assertIs<PortfolioEffect.ScrollTo>(effect)
        assertEquals(800 - 100, effect.y)
    }

    @Test
    fun navigateToUnknownSectionEmitsNothing() = runTest {
        val store = createStore()
        val received = mutableListOf<PortfolioEffect>()
        val collector = launch(UnconfinedTestDispatcher(testScheduler)) {
            store.effects.toList(received)
        }

        store.dispatch(PortfolioIntent.NavigateToSection(Section.CONNECT))
        runCurrent()

        assertTrue(received.isEmpty())
        collector.cancel()
    }

    @Test
    fun openLinkEmitsOpenLinkEffect() = runTest {
        val store = createStore()

        store.dispatch(PortfolioIntent.OpenLink("https://example.com"))

        val effect = store.effects.first()
        assertIs<PortfolioEffect.OpenLink>(effect)
        assertEquals("https://example.com", effect.url)
    }

    private class FakePortfolioRepository : PortfolioRepository {

        override fun getProfile(): Profile = Profile(
            fullName = "Test User Full",
            displayName = "Test User",
            handle = "@test",
            title = "Test Engineer",
            tagline = "tagline",
            email = "test@example.com",
            links = listOf(ProfileLink("github", "https://github.com/test", "https://github.com/test")),
            heroCodeLines = listOf("fun main() {}"),
            about = "about",
            stackNote = "stack note",
            stack = listOf("Kotlin"),
            facts = listOf(Fact("1+", "Years")),
            availability = "available",
            footerNote = "footer",
        )

        override fun getProjects(): List<Project> = listOf(
            Project(
                id = "alpha",
                index = "01",
                title = "Alpha",
                role = "Engineer",
                blurb = "blurb",
                story = "story",
                highlights = listOf("highlight"),
                year = "2024",
                category = "Test",
                link = "https://example.com/alpha",
                linkType = LinkType.PLAY_STORE,
                stack = listOf("Kotlin"),
                brand = Brand.MY_XL,
                screenshotIds = listOf("alpha_1"),
            ),
            Project(
                id = "beta",
                index = "02",
                title = "Beta",
                role = "Engineer",
                blurb = "blurb",
                story = "story",
                highlights = listOf("highlight"),
                year = "2025",
                category = "Test",
                link = null,
                linkType = LinkType.NONE,
                stack = listOf("Kotlin"),
                brand = Brand.TRACK_FIT,
                screenshotIds = listOf("beta_1"),
            ),
        )
    }
}
