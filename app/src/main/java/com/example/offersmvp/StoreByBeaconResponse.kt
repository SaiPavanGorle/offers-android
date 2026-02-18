package com.example.offersmvp

import com.google.gson.annotations.SerializedName

data class StoreByBeaconResponse(
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
    val description: String,
    @SerializedName("banner_url") val bannerUrl: String?,
    @SerializedName("start_at") val startAt: String?,
    @SerializedName("end_at") val endAt: String?
)
