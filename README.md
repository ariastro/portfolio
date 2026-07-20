# Ariastro Portfolio

Professional portfolio built with **Compose Multiplatform (Wasm)**.

## Run

```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

Open the URL Gradle prints (usually `http://localhost:8080`).

## Build production

```bash
./gradlew :composeApp:wasmJsBrowserDistribution
```

Output: `composeApp/build/dist/wasmJs/productionExecutable/`

## Deploy to GitHub Pages

1. Push to `main` branch
2. GitHub Actions auto-deploys to `https://<username>.github.io/personal-portfolio/`
3. Configure custom domain in Settings → Pages → Custom domain

## Stack

- Kotlin Multiplatform
- Compose Multiplatform (Material 3)
- Kotlin/Wasm target

## Content

Edit project copy and links in:

`composeApp/src/commonMain/kotlin/com/ariastro/portfolio/data/Project.kt`

---

Built with ❤️ using Compose Multiplatform.