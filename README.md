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
