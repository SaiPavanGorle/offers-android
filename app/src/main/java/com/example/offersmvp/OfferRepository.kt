package com.example.offersmvp

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

class OfferRepository {
    private val apiService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)

    suspend fun getOffers(uuid: String, major: Int, minor: Int): List<Offer> {
        val response = apiService.getStoreCampaign(uuid, major, minor)
        return response.activeCampaign
            ?.let { campaign ->
                Offer(
                    id = campaign.campaignId,
                    title = campaign.title,
                    description = campaign.description
                )
            }
            ?.let { offer ->
                listOf(offer)
            }
            ?: emptyList()
    }
}

interface ApiService {
    @GET("v1/public/stores/by-ibeacon")
    suspend fun getStoreCampaign(
        @Query("uuid") uuid: String,
        @Query("major") major: Int,
        @Query("minor") minor: Int
    ): StoreCampaignResponse
}

data class StoreCampaignResponse(
    val store: StoreDto,
    @SerializedName("active_campaign") val activeCampaign: CampaignDto?
)

data class StoreDto(
    val id: String,
    val name: String
)

data class CampaignDto(
    @SerializedName("campaign_id") val campaignId: String,
    val title: String,
    val description: String
)
