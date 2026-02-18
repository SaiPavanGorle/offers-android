# Offers Android MVP

This repository contains a minimal Android MVP sample app implemented in Kotlin.

## Project structure

- `app/src/main/java/com/example/offersmvp` — MVP contract, presenter, repository, Retrofit client, and Activity.
- `app/src/main/res` — layouts and app resources.
- Gradle build scripts for root and app modules.
- Gradle wrapper scripts and wrapper properties.

## Architecture (MVP)

- **View**: `MainActivity` implements `OfferContract.View`.
- **Presenter**: `OfferPresenter` orchestrates loading and presentation logic.
- **Model**: `OfferRepository` supplies data from the backend using Retrofit.

## Build

Use your local Android SDK and run:

```bash
./gradlew assembleDebug
```

> Note: This repository intentionally excludes IDE files, build outputs, keystores, and other binary artifacts.

## Base URL configuration

The app currently reads API base URL from `BuildConfig.BASE_URL_DEVICE` via `AppConfig.baseUrl`.

Configured values in `app/build.gradle.kts`:

- `BASE_URL_EMULATOR` = `http://10.0.2.2:8000/`
- `BASE_URL_DEVICE` = `http://192.168.1.59:8000/`

To test on a real phone, ensure `BASE_URL_DEVICE` points to your backend host IP on the same network.

## How to test enquiry on phone

1. Connect your Android phone to the same Wi-Fi network as your backend server.
2. Update `BASE_URL_DEVICE` in `app/build.gradle.kts` if needed (IP + port).
3. Build and install the app on the phone:
   ```bash
   ./gradlew installDebug
   ```
4. Open the app near/using the demo iBeacon values already configured in `AppConfig`.
5. Confirm campaign details load (banner, title, description, validity).
6. Tap **I'm Interested**, enter a message, and tap **Submit**.
7. Verify success toast is shown and backend receives `POST /v1/public/enquiries` with:
   - `store_id`
   - `campaign_id`
   - `device_anon_id`
   - `message`
8. Optional: tap **Open Website** to verify campaign/store URL opens in browser.
