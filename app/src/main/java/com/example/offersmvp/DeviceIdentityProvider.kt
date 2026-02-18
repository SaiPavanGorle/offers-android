package com.example.offersmvp

import android.content.Context
import java.util.UUID

object DeviceIdentityProvider {
    private const val PREF_NAME = "offers_prefs"
    private const val KEY_DEVICE_ANON_ID = "device_anon_id"

    @Volatile
    private var appContext: Context? = null

    fun initialize(context: Context) {
        appContext = context.applicationContext
    }

    fun getDeviceAnonId(): String {
        val context = requireNotNull(appContext) { "DeviceIdentityProvider is not initialized" }
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val existing = prefs.getString(KEY_DEVICE_ANON_ID, null)
        if (!existing.isNullOrBlank()) return existing

        val generated = UUID.randomUUID().toString()
        prefs.edit().putString(KEY_DEVICE_ANON_ID, generated).apply()
        return generated
    }
}
