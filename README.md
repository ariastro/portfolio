# Ari SWS - Personal Portfolio

[![Deploy to GitHub Pages](https://github.com/ariastro/portfolio/actions/workflows/deploy.yml/badge.svg)](https://github.com/ariastro/portfolio/actions/workflows/deploy.yml)

Source code for my personal portfolio website.

🌍 **[Live Portfolio Link](https://ariastro.github.io/portfolio)**

## Tech Stack

This project is built using:
- **Compose Multiplatform** (Material 3)
- **Kotlin/Wasm** target

## Features

- 🚀 Fully built with Compose for Web (Wasm)
- 📱 Responsive design
- 🎨 Material 3 UI/UX

## Architecture

The app follows **Clean Architecture** with a strict dependency rule (domain depends on
nothing; data, presentation and UI depend inward) and a single **MVI** store for the UI.

- **domain** — pure Kotlin models (`Project`, `Profile`, `Section`…), the
  `PortfolioRepository` contract and use cases. No Compose/framework imports.
- **data** — static content plus the concrete `PortfolioRepositoryImpl`.
- **presentation** — the MVI `PortfolioStore`: immutable `PortfolioUiState`,
  `PortfolioIntent`s in, one-shot `PortfolioEffect`s out. Depends only on domain.
- **ui** — stateless composables that render state and forward events; framework
  concerns (colors, drawable assets) are resolved here, never in domain.

## Project Structure

```text
.
├── composeApp/
│   ├── src/
│   │   ├── commonMain/
│   │   │   └── kotlin/com/ariastro/portfolio/
│   │   │       ├── App.kt               # Composition root, wires the store to the UI
│   │   │       ├── domain/              # Models, repository contract, use cases
│   │   │       ├── data/                # Static content + repository implementation
│   │   │       ├── presentation/        # MVI store, state, intents, effects
│   │   │       ├── ui/
│   │   │       │   ├── components/      # Reusable UI parts (Layout, Patterns)
│   │   │       │   ├── sections/        # Main page sections (stateless)
│   │   │       │   └── theme/           # Colors, Typography, Theme
│   │   ├── commonTest/                  # Unit tests for the store
│   │   ├── wasmJsMain/                  # Wasm specific code
├── build.gradle.kts                     # Root build script
└── settings.gradle.kts                  # Project settings
```

## Contact

- **Email**: [ariastronout@gmail.com](mailto:your.email@example.com)
- **LinkedIn**: [https://www.linkedin.com/in/arisws/](https://linkedin.com/in/yourusername)
- **GitHub**: [https://github.com/ariastro](https://github.com/yourusername)

---
Built with ❤️ using Compose Multiplatform.
