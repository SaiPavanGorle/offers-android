# Offers Android MVP

This repository contains a minimal Android MVP sample app implemented in Kotlin.

## Project structure

- `app/src/main/java/com/example/offersmvp` — MVP contract, presenter, repository, and Activity.
- `app/src/main/res` — layouts and app resources.
- Gradle build scripts for root and app modules.
- Gradle wrapper scripts and wrapper properties.

## Architecture (MVP)

- **View**: `MainActivity` implements `OfferContract.View`.
- **Presenter**: `OfferPresenter` orchestrates loading and presentation logic.
- **Model**: `OfferRepository` supplies data via `Offer` model.

## Build

Use your local Android SDK and run:

```bash
./gradlew assembleDebug
```

> Note: This repository intentionally excludes IDE files, build outputs, keystores, and other binary artifacts.

## Base URL configuration

The app defines three `BuildConfig` fields in `app/build.gradle.kts`:

- `BASE_URL_EMULATOR` = `http://10.0.2.2:8000/`
- `BASE_URL_DEVICE` = `http://192.168.1.59:8000/`
- `BASE_URL_DEFAULT` = active URL used by the app (currently set to emulator)

To switch between emulator and real device:

1. Open `app/build.gradle.kts`.
2. In `defaultConfig`, change `BASE_URL_DEFAULT` to one of:
   - `BASE_URL_EMULATOR` (Android Emulator)
   - `BASE_URL_DEVICE` (real phone on the same network)
3. Sync/rebuild the project.

`AppConfig.baseUrl` currently returns `BuildConfig.BASE_URL_DEFAULT` and is the single place to read the active API URL in code.
