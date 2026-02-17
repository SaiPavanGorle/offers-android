package com.example.offersmvp

import android.os.Build

object AppConfig {
    val baseUrl: String
        get() = if (isEmulator()) BuildConfig.BASE_URL_EMULATOR else BuildConfig.BASE_URL_DEVICE

    private fun isEmulator(): Boolean {
        return Build.FINGERPRINT.startsWith("generic") ||
            Build.FINGERPRINT.startsWith("unknown") ||
            Build.MODEL.contains("google_sdk") ||
            Build.MODEL.contains("Emulator") ||
            Build.MODEL.contains("Android SDK built for x86")
    }
}
