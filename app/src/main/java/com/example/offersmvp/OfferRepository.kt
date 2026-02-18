package com.example.offersmvp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferRepository(
    private val offersApi: OffersApi = RetrofitClient.offersApi
) {
    fun getActiveOffer(
        uuid: String,
        major: Int,
        minor: Int,
        onResult: (Offer?) -> Unit
    ) {
        offersApi.getStoreByBeacon(uuid, major, minor).enqueue(object : Callback<StoreByBeaconResponse> {
            override fun onResponse(
                call: Call<StoreByBeaconResponse>,
                response: Response<StoreByBeaconResponse>
            ) {
                if (!response.isSuccessful) {
                    onResult(null)
                    return
                }

                val offer = response.body()
                    ?.activeCampaign
                    ?.toOffer()

                onResult(offer)
            }

            override fun onFailure(call: Call<StoreByBeaconResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }

    private fun CampaignDto.toOffer(): Offer {
        return Offer(
            id = campaignId,
            title = title,
            description = description
        )
    }
}
