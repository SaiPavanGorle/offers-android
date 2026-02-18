package com.example.offersmvp

import com.google.gson.annotations.SerializedName

data class StoreByBeaconResponse(
    val store: StoreDto,
    @SerializedName("active_campaign") val activeCampaign: CampaignDto?
)

data class StoreDto(
    @SerializedName("store_id") val storeId: String,
    val name: String,
    @SerializedName("website_url") val websiteUrl: String?
)

data class CampaignDto(
    @SerializedName("campaign_id") val campaignId: String,
    val title: String,
    val description: String,
    @SerializedName("banner_url") val bannerUrl: String?,
    @SerializedName("website_url") val websiteUrl: String?,
    @SerializedName("start_at") val startAt: String?,
    @SerializedName("end_at") val endAt: String?
)

data class EnquiryRequest(
    @SerializedName("store_id") val storeId: String,
    @SerializedName("campaign_id") val campaignId: String,
    @SerializedName("device_anon_id") val deviceAnonId: String,
    val message: String
)

data class EnquiryResponse(
    val success: Boolean? = null,
    val message: String? = null
)
