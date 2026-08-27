package com.ariastro.portfolio.data

import com.ariastro.portfolio.domain.model.Brand
import com.ariastro.portfolio.domain.model.Experience
import com.ariastro.portfolio.domain.model.Fact
import com.ariastro.portfolio.domain.model.LinkType
import com.ariastro.portfolio.domain.model.Profile
import com.ariastro.portfolio.domain.model.ProfileLink
import com.ariastro.portfolio.domain.model.Project

/**
 * Static portfolio content — the single source of truth for copy and project data.
 * Kept free of any UI/framework types so the domain stays clean; visual attributes
 * (accent colors, drawable assets) are resolved from [Brand] and screenshot ids in the UI layer.
 */
internal object PortfolioContent {

    private const val EMAIL = "ariastronout@gmail.com"
    private const val GITHUB = "https://github.com/ariastro"
    private const val LINKEDIN = "https://www.linkedin.com/in/arisws/"
    private const val UPWORK = "https://www.upwork.com/freelancers/~01fb69fd0823d1e910?viewMode=1"

    val profile = Profile(
        fullName = "Ari Sastro Wardoyo Supiatma",
        displayName = "Ari SWS",
        handle = "@ariastro",
        title = "Android Engineer",
        tagline = "I ship production Android systems — not demos. " +
            "From telecom scale to personal side builds.",
        email = EMAIL,
        links = listOf(
            ProfileLink(label = "email", value = EMAIL, url = "mailto:$EMAIL"),
            ProfileLink(label = "github", value = GITHUB, url = GITHUB),
            ProfileLink(label = "linkedin", value = LINKEDIN, url = LINKEDIN),
            ProfileLink(label = "upwork", value = UPWORK, url = UPWORK),
        ),
        heroCodeLines = listOf(
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
        ),
        about = "I build Android apps for a living — the kind that real people open every day, " +
            "not just demos that look pretty in a deck. Kotlin is home. Compose when it fits, " +
            "Views when the codebase says so. I like Clean Architecture not as a religion, " +
            "but because it keeps me sane when features pile up.\n\n" +
            "I've shipped across telecom, school transport, gaming, farming tools, fitness — " +
            "messy production code and greenfield alike. Happy digging into modularization, " +
            "maps, payments, offline, or why something crashed at 2am. If it ships and stays " +
            "readable, that's a good day.",
        stackNote = "Tools I reach for when shipping Android products.",
        stack = listOf(
            "Kotlin",
            "Jetpack Compose",
            "Material 3",
            "Clean Architecture",
            "MVVM / MVI",
            "Modularization",
            "Coroutines",
            "Flow",
            "Hilt / Koin",
            "Room",
            "DataStore",
            "Retrofit / Ktor",
            "WorkManager",
            "Firebase",
            "Google Maps",
            "Stripe",
            "Compose Multiplatform",
            "Unit tests",
        ),
        facts = listOf(
            Fact("7+", "Years Android"),
            Fact("10M+", "App Users"),
            Fact("~99.8%", "Crash-Free"),
            Fact("KMP", "Multiplatform"),
        ),
        experiences = listOf(
            Experience(
                role = "Android Developer",
                company = "Aleph Labs",
                period = "Feb 2024 — Present",
                summary = "Maintained and developed the MyXL application — XL Axiata's telecom " +
                    "super-app for packages, balance and bills — working in a large modular " +
                    "Kotlin codebase built on Clean Architecture.",
                highlights = listOf(
                    "Feature delivery on MyXL's modular codebase",
                    "Kotlin + Clean Architecture at production scale",
                ),
            ),
            Experience(
                role = "External Code Reviewer",
                company = "Dicoding Indonesia",
                period = "Dec 2023 — Present · part-time",
                summary = "Reviewing Dicoding students' Android submissions — reading other " +
                    "people's Kotlin for correctness, architecture and readability, and writing " +
                    "the feedback that helps them improve.",
                highlights = listOf(
                    "Feedback on Kotlin idiom and clean architecture",
                ),
            ),
            Experience(
                role = "Android Developer",
                company = "Tekuton",
                period = "Dec 2023 — Nov 2025 · part-time",
                summary = "Maintained and extended the TagTag Android app — new features on a " +
                    "live consumer product, plus the in-app purchase layer via Google Play " +
                    "Billing.",
                highlights = listOf(
                    "Google Play Billing for in-app purchases",
                    "New features on a shipped, actively-used app",
                    "Ongoing maintenance and stability work",
                ),
            ),
            Experience(
                role = "Android Engineer",
                company = "Agree",
                period = "Nov 2021 — Feb 2024 · part-time",
                summary = "SuperAgree — cultivation monitoring across agriculture, fisheries and " +
                    "livestock. Dynamic forms, partnership flows and activity tracking for " +
                    "field users in a modular app with dynamic feature modules.",
                highlights = listOf(
                    "Cultivation activity tracking end to end",
                    "Dynamic forms and dynamic feature delivery",
                    "Koin + Room for offline-friendly paths",
                ),
            ),
            Experience(
                role = "Android Developer",
                company = "Nomina Games",
                period = "Sep 2023 — Dec 2023",
                summary = "TagTag — an online gaming platform where players earn through play or " +
                    "affiliate. Shipped high-energy raffle, mission and leaderboard loops with " +
                    "ads, payments and crash monitoring.",
                highlights = listOf(
                    "AdMob + payment gateway integration",
                    "Compose interop with a legacy Views codebase",
                    "Crashlytics-driven stability work",
                ),
            ),
            Experience(
                role = "Android Developer",
                company = "PT XL Axiata Tbk",
                period = "Dec 2021 — Aug 2023",
                summary = "MyXL — the telecom super-app for packages, balance, bills and store " +
                    "locations. Worked inside a large modular Clean Architecture codebase on " +
                    "the daily-use flows millions of subscribers depend on.",
                highlights = listOf(
                    "Package purchase and balance flows",
                    "Modularization + Clean Architecture at scale",
                    "Maps, WorkManager, Remote Config",
                    "Unit coverage on critical paths",
                ),
            ),
            Experience(
                role = "Android Developer",
                company = "Dalenta",
                period = "Apr 2021 — Dec 2021",
                summary = "Dalenta POS and Dalenta Kitchen — a point-of-sale app and its paired " +
                    "kitchen display system, built in Kotlin for tablet and kiosk hardware " +
                    "rather than phones.",
                highlights = listOf(
                    "POS and kitchen-display apps in Kotlin",
                    "Tablet and kiosk form factors",
                ),
            ),
            Experience(
                role = "Mobile Developer",
                company = "Nakula Sadewa",
                period = "Oct 2020 — Mar 2021",
                summary = "A queue-management app running on kiosks in public-service settings — " +
                    "puskesmas, banks and similar — where the screen is unattended hardware and " +
                    "has to keep working on its own.",
                highlights = listOf(
                    "Kiosk queue app in Kotlin",
                    "Deployed to puskesmas, banks and similar venues",
                ),
            ),
            Experience(
                role = "Android Developer",
                company = "DOT Indonesia",
                period = "Jul 2019 — Oct 2019 · internship",
                summary = "First production Android work — learning a real Kotlin codebase and " +
                    "shipping features alongside the team.",
            ),
        ),
        availability = "Open to Android / KMP roles, freelance, collaboration.",
        footerNote = "Made with Kotlin",
    )

    val projects = listOf(
        Project(
            id = "myxl",
            index = "01",
            title = "MyXL",
            role = "Android Engineer",
            blurb = "Telecom super-app for XL Axiata — packages, balance, bills, maps.",
            story = "MyXL is XL Axiata’s consumer app for managing mobile data, buying packages, " +
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
            linkType = LinkType.PLAY_STORE,
            stack = listOf(
                "Kotlin", "MVVM", "Hilt", "Retrofit", "Room", "WorkManager",
                "Google Maps", "Modularization", "Clean Architecture",
            ),
            brand = Brand.MY_XL,
            screenshotIds = listOf("myxl_1", "myxl_2"),
        ),
        Project(
            id = "schoolryde",
            index = "02",
            title = "SchoolRyde",
            role = "Android Engineer",
            blurb = "Parent-facing school transport with live ride tracking.",
            story = "SchoolRyde connects parents with trusted pick-up and drop-off for students. " +
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
            linkType = LinkType.PLAY_STORE,
            stack = listOf(
                "Jetpack Compose", "MVI", "Hilt", "Google Maps", "Places API",
                "Stripe", "Room", "Firebase", "DataStore",
            ),
            brand = Brand.SCHOOL_RYDE,
            screenshotIds = listOf("schoolryde_1", "schoolryde_2", "schoolryde_3", "schoolryde_4"),
        ),
        Project(
            id = "tagtag",
            index = "03",
            title = "TagTag",
            role = "Android Engineer",
            blurb = "Gaming platform — play, affiliate, leaderboards, payments.",
            story = "TagTag is an online gaming platform where players earn via play or affiliate. " +
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
            linkType = LinkType.PLAY_STORE,
            stack = listOf(
                "Kotlin", "Compose", "Hilt", "Room", "AdMob",
                "Firebase", "Crashlytics", "Payment Gateway",
            ),
            brand = Brand.TAG_TAG,
            screenshotIds = listOf("tagtag_1", "tagtag_2", "tagtag_3"),
        ),
        Project(
            id = "superagree",
            index = "04",
            title = "SuperAgree",
            role = "Android Engineer",
            blurb = "Agri / fishery / livestock cultivation monitoring super-app.",
            story = "SuperAgree helps partners monitor cultivation across agriculture, fisheries, " +
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
            linkType = LinkType.PLAY_STORE,
            stack = listOf(
                "Kotlin", "MVVM", "Koin", "RxJava", "Room",
                "DataStore", "Dynamic Features", "Firebase",
            ),
            brand = Brand.SUPER_AGREE,
            screenshotIds = listOf("superagree_1", "superagree_2"),
        ),
        Project(
            id = "trackfit",
            index = "05",
            title = "TrackFit",
            role = "Android Engineer",
            blurb = "PT session + workout tracker for trainers in Korea.",
            story = "TrackFit helps trainers and members log personal training sessions and " +
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
            linkType = LinkType.PLAY_STORE,
            stack = listOf(
                "Kotlin", "MVVM", "Hilt", "Flow", "Paging3",
                "Firebase", "Moshi", "Custom Calendar",
            ),
            brand = Brand.TRACK_FIT,
            screenshotIds = listOf("trackfit_1", "trackfit_2", "trackfit_3"),
        ),
    )
}
