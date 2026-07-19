package com.ariastro.portfolio.data

import androidx.compose.ui.graphics.Color
import com.ariastro.portfolio.resources.Res
import com.ariastro.portfolio.resources.project_myxl
import com.ariastro.portfolio.resources.project_schoolryde
import com.ariastro.portfolio.resources.project_superagree
import com.ariastro.portfolio.resources.project_tagtag
import com.ariastro.portfolio.resources.project_trackfit
import org.jetbrains.compose.resources.DrawableResource

data class Project(
    val id: String,
    val index: String,
    val title: String,
    val role: String,
    val blurb: String,
    val story: String,
    val highlights: List<String>,
    val year: String,
    val category: String,
    val link: String?,
    val linkType: LinkType,
    val stack: List<String>,
    val accent: Color,
    val screenshot: DrawableResource,
    val status: String = "SHIPPED",
)

enum class LinkType {
    PlayStore,
    GitHub,
    Website,
    None,
}

object PortfolioData {
    const val FULL_NAME = "Ari Sastro Wardoyo Supiatma"
    const val NAME = "Ari SWS"
    const val HANDLE = "@ariastro"
    const val TITLE = "Android Engineer"
    const val EMAIL = "ariastronout@gmail.com"
    const val GITHUB = "https://github.com/ariastro"
    const val LINKEDIN = "https://www.linkedin.com/in/arisws/"

    val heroLines = listOf(
        "package id.ariastro.portfolio",
        "",
        "/**",
        " * Ships production Android apps.",
        " * Kotlin · Compose · Clean Architecture",
        " */",
        "fun main() {",
        "    build()",
        "    ship()",
        "    iterate()",
        "}",
    )

    const val ABOUT =
        "I own features end-to-end — architecture, modularization, " +
            "networking, offline, maps, payments, and Play Store delivery. " +
            "Comfortable in messy production codebases and greenfield Compose apps."

    val facts = listOf(
        "Kotlin" to "primary language",
        "Compose" to "UI toolkit",
        "Clean Arch" to "default approach",
    )

    val stackGroups = listOf(
        "ui" to listOf("Jetpack Compose", "Material 3", "ViewBinding", "XML"),
        "arch" to listOf("Clean Architecture", "MVVM", "MVI", "Modularization"),
        "async" to listOf("Coroutines", "Flow", "WorkManager"),
        "data" to listOf("Room", "DataStore", "Retrofit", "Ktor", "Moshi"),
        "di" to listOf("Hilt", "Koin", "Dagger"),
        "platform" to listOf("Firebase", "Maps", "Stripe", "AdMob", "KMP"),
    )

    val projects = listOf(
        Project(
            id = "myxl",
            index = "01",
            title = "MyXL",
            role = "Android Engineer",
            blurb = "Telecom super-app for XL Axiata — packages, balance, bills, maps.",
            story =
                "MyXL is XL Axiata’s consumer app for managing mobile data, buying packages, " +
                    "checking balances, and paying bills. Worked inside a large modular codebase " +
                    "with Clean Architecture, focusing on reliable daily-use flows for millions of users.",
            highlights = listOf(
                "Package purchase & balance flows",
                "Modularization + Clean Architecture",
                "Maps, WorkManager, Remote Config",
                "Unit coverage on critical paths",
            ),
            year = "2022+",
            category = "Telecom",
            link = "https://play.google.com/store/apps/details?id=com.apps.MyXL",
            linkType = LinkType.PlayStore,
            stack = listOf(
                "Kotlin", "MVVM", "Hilt", "Retrofit", "Room", "WorkManager",
                "Google Maps", "Modularization", "Clean Architecture",
            ),
            accent = Color(0xFF2563EB),
            screenshot = Res.drawable.project_myxl,
        ),
        Project(
            id = "schoolryde",
            index = "02",
            title = "SchoolRyde",
            role = "Android Engineer",
            blurb = "Parent-facing school transport with live ride tracking.",
            story =
                "SchoolRyde connects parents with trusted pick-up and drop-off for students. " +
                    "Built Compose screens for scheduled rides, live map tracking, and driver " +
                    "context — MVI + Maps/Places for a calm, safety-first experience.",
            highlights = listOf(
                "Live ride tracking on Maps",
                "Schedule / family / safety tabs",
                "MVI + Clean Architecture",
                "Stripe + Firebase integrations",
            ),
            year = "2024+",
            category = "Mobility",
            link = "https://play.google.com/store/apps/details?id=com.schoolryde.parent",
            linkType = LinkType.PlayStore,
            stack = listOf(
                "Jetpack Compose", "MVI", "Hilt", "Google Maps", "Places API",
                "Stripe", "Room", "Firebase", "DataStore",
            ),
            accent = Color(0xFF0D9488),
            screenshot = Res.drawable.project_schoolryde,
        ),
        Project(
            id = "tagtag",
            index = "03",
            title = "TagTag",
            role = "Android Engineer",
            blurb = "Gaming platform — play, affiliate, leaderboards, payments.",
            story =
                "TagTag is an online gaming platform where players earn via play or affiliate. " +
                    "Shipped high-energy UI (raffles, missions, ranks) with ads, payments, and " +
                    "crash monitoring for a volatile consumer product.",
            highlights = listOf(
                "Raffle / mission / leaderboard loops",
                "AdMob + payment gateway",
                "Compose mixed with legacy views",
                "Crashlytics + Firebase ops",
            ),
            year = "2023+",
            category = "Consumer",
            link = "https://play.google.com/store/apps/details?id=gg.tagtag.app",
            linkType = LinkType.PlayStore,
            stack = listOf(
                "Kotlin", "Compose", "Hilt", "Room", "AdMob",
                "Firebase", "Crashlytics", "Payment Gateway",
            ),
            accent = Color(0xFF16A34A),
            screenshot = Res.drawable.project_tagtag,
        ),
        Project(
            id = "superagree",
            index = "04",
            title = "SuperAgree",
            role = "Android Engineer",
            blurb = "Agri / fishery / livestock cultivation monitoring super-app.",
            story =
                "SuperAgree helps partners monitor cultivation across agriculture, fisheries, " +
                    "and livestock. Delivered dynamic forms, partnership flows, and activity " +
                    "tracking for field users — modular app with dynamic features.",
            highlights = listOf(
                "Cultivation activity tracking",
                "Dynamic forms & features",
                "Partner / mitra company flows",
                "Koin + Room offline-friendly paths",
            ),
            year = "2022+",
            category = "AgriTech",
            link = "https://play.google.com/store/apps/details?id=com.agree.ecosystem",
            linkType = LinkType.PlayStore,
            stack = listOf(
                "Kotlin", "MVVM", "Koin", "RxJava", "Room",
                "DataStore", "Dynamic Features", "Firebase",
            ),
            accent = Color(0xFF65A30D),
            screenshot = Res.drawable.project_superagree,
        ),
        Project(
            id = "trackfit",
            index = "05",
            title = "TrackFit",
            role = "Android Engineer",
            blurb = "PT session + workout tracker for trainers in Korea.",
            story =
                "TrackFit helps trainers and members log personal training sessions and " +
                    "individual workouts. Custom calendar, progress views, and paging-backed " +
                    "lists for long training histories.",
            highlights = listOf(
                "Custom calendar scheduling",
                "PT session + workout logs",
                "Paging3 for long histories",
                "Firebase-backed sync",
            ),
            year = "2023",
            category = "Health",
            link = "https://play.google.com/store/apps/details?id=com.jaden.fitnessapp",
            linkType = LinkType.PlayStore,
            stack = listOf(
                "Kotlin", "MVVM", "Hilt", "Flow", "Paging3",
                "Firebase", "Moshi", "Custom Calendar",
            ),
            accent = Color(0xFFDC2626),
            screenshot = Res.drawable.project_trackfit,
        ),
    )
}
