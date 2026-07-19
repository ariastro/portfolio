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

## Stack

- Kotlin Multiplatform
- Compose Multiplatform (Material 3)
- Kotlin/Wasm target

## Content

Edit project copy and links in:

`composeApp/src/commonMain/kotlin/com/ariastro/portfolio/data/Project.kt`
