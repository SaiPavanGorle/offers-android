package com.example.offersmvp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferRepository(
    private val apiService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)
) {
    fun fetchStoreAndCampaign(
        uuid: String,
        major: Int,
        minor: Int,
        onResult: (Result<StoreByBeaconResponse>) -> Unit
    ) {
        apiService.getStoreByBeacon(uuid, major, minor).enqueue(object : Callback<StoreByBeaconResponse> {
            override fun onResponse(
                call: Call<StoreByBeaconResponse>,
                response: Response<StoreByBeaconResponse>
            ) {
                if (!response.isSuccessful) {
                    onResult(Result.failure(RuntimeException("Failed to load campaign (${response.code()})")))
                    return
                }

                val body = response.body()
                if (body == null) {
                    onResult(Result.failure(RuntimeException("Empty response from server")))
                    return
                }

                onResult(Result.success(body))
            }

            override fun onFailure(call: Call<StoreByBeaconResponse>, t: Throwable) {
                onResult(Result.failure(t))
            }
        })
    }

    fun postEnquiry(
        request: EnquiryCreate,
        onResult: (Result<EnquiryResponse>) -> Unit
    ) {
        apiService.postEnquiry(request).enqueue(object : Callback<EnquiryResponse> {
            override fun onResponse(call: Call<EnquiryResponse>, response: Response<EnquiryResponse>) {
                if (response.isSuccessful) {
                    onResult(Result.success(response.body() ?: EnquiryResponse()))
                } else {
                    onResult(Result.failure(RuntimeException("Failed to send enquiry (${response.code()})")))
                }
            }

            override fun onFailure(call: Call<EnquiryResponse>, t: Throwable) {
                onResult(Result.failure(t))
            }
        })
    }
}
