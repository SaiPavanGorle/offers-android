package com.example.offersmvp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferRepository(
    private val offersApi: OffersApi = RetrofitClient.offersApi
) {
    fun getStoreWithCampaign(
        uuid: String,
        major: Int,
        minor: Int,
        onResult: (StoreDto?, CampaignDto?) -> Unit
    ) {
        offersApi.getStoreByBeacon(uuid, major, minor).enqueue(object : Callback<StoreByBeaconResponse> {
            override fun onResponse(
                call: Call<StoreByBeaconResponse>,
                response: Response<StoreByBeaconResponse>
            ) {
                if (!response.isSuccessful) {
                    onResult(null, null)
                    return
                }

                val body = response.body()
                onResult(body?.store, body?.activeCampaign)
            }

            override fun onFailure(call: Call<StoreByBeaconResponse>, t: Throwable) {
                onResult(null, null)
            }
        })
    }

    fun submitEnquiry(
        request: EnquiryRequest,
        onResult: (Boolean) -> Unit
    ) {
        offersApi.submitEnquiry(request).enqueue(object : Callback<EnquiryResponse> {
            override fun onResponse(call: Call<EnquiryResponse>, response: Response<EnquiryResponse>) {
                onResult(response.isSuccessful)
            }

            override fun onFailure(call: Call<EnquiryResponse>, t: Throwable) {
                onResult(false)
            }
        })
    }
}
