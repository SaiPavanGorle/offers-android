package com.example.offersmvp

object AppConfig {
    const val DEMO_UUID = "F7826DA6-4FA2-4E98-8024-BC5B71E0893E"
    const val DEMO_MAJOR = 1001
    const val DEMO_MINOR = 1

    val baseUrl: String
        get() = if (isRunningOnEmulator()) {
            BuildConfig.BASE_URL_EMULATOR
        } else {
            BuildConfig.BASE_URL_DEVICE
        }

    private fun isRunningOnEmulator(): Boolean {
        val fingerprint = android.os.Build.FINGERPRINT.lowercase()
        val model = android.os.Build.MODEL.lowercase()
        val brand = android.os.Build.BRAND.lowercase()
        val device = android.os.Build.DEVICE.lowercase()
        val product = android.os.Build.PRODUCT.lowercase()

        return fingerprint.contains("generic") ||
            fingerprint.contains("emulator") ||
            model.contains("emulator") ||
            model.contains("android sdk built for") ||
            brand.contains("generic") && device.contains("generic") ||
            product.contains("sdk") ||
            product.contains("emulator")
    }
}
