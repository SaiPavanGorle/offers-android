package com.example.offersmvp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OffersApi {
    @GET("v1/public/stores/by-ibeacon")
    fun getStoreByBeacon(
        @Query("uuid") uuid: String,
        @Query("major") major: Int,
        @Query("minor") minor: Int
    ): Call<StoreByBeaconResponse>
}
