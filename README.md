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

## Project Structure

```text
.
├── composeApp/
│   ├── src/
│   │   ├── commonMain/
│   │   │   └── kotlin/com/ariastro/portfolio/
│   │   │       ├── App.kt                 # Main app entry
│   │   │       ├── data/Project.kt        # Project data models
│   │   │       ├── ui/
│   │   │       │   ├── components/        # Reusable UI parts (Layout, Patterns)
│   │   │       │   ├── sections/          # Main page sections
│   │   │       │   └── theme/             # Colors, Typography, Theme
│   │   ├── wasmJsMain/                    # Wasm specific code
├── build.gradle.kts                       # Root build script
└── settings.gradle.kts                    # Project settings
```

## Contact

- **Email**: [ariastronout@gmail.com](mailto:your.email@example.com)
- **LinkedIn**: [https://www.linkedin.com/in/arisws/](https://linkedin.com/in/yourusername)
- **GitHub**: [https://github.com/ariastro](https://github.com/yourusername)

---
Built with ❤️ using Compose Multiplatform.
