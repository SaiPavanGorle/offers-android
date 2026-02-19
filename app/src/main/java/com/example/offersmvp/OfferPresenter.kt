package com.example.offersmvp

class OfferPresenter(
    private val repository: OfferRepository
) : OfferContract.Presenter {

    private var view: OfferContract.View? = null
    private var currentStore: StoreDto? = null
    private var currentCampaign: CampaignDto? = null

    override fun attach(view: OfferContract.View) {
        this.view = view
    }

    override fun detach() {
        view = null
    }

    override fun loadOffers(uuid: String, major: Int, minor: Int) {
        val currentView = view ?: return
        currentView.showLoading()

        repository.fetchStoreAndCampaign(uuid, major, minor) { result ->
            val activeView = view ?: return@fetchStoreAndCampaign
            result.onSuccess { response ->
                val campaign = response.activeCampaign
                if (campaign == null || !campaign.isActive) {
                    activeView.showError("No active campaign available.")
                } else {
                    currentStore = response.store
                    currentCampaign = campaign
                    activeView.showCampaign(
                        title = campaign.title,
                        description = campaign.description,
                        validText = formatValidity(campaign.startAt, campaign.endAt),
                        websiteUrl = campaign.websiteUrl
                    )
                }
            }.onFailure { error ->
                activeView.showError(error.message ?: "Failed to load offers")
            }

            activeView.hideLoading()
        }
    }

    override fun onOpenWebsiteClicked() {
        val activeView = view ?: return
        val websiteUrl = currentCampaign?.websiteUrl ?: currentStore?.websiteUrl

        if (websiteUrl.isNullOrBlank()) {
            activeView.showToast("Website URL unavailable")
            return
        }

        activeView.openUrl(websiteUrl)
    }

    override fun onInterestedClicked() {
        val activeView = view ?: return
        val store = currentStore
        val campaign = currentCampaign

        if (store == null || campaign == null) {
            activeView.showToast("Invalid enquiry details")
            return
        }

        val request = EnquiryCreate(
            enquiryId = java.util.UUID.randomUUID().toString(),
            storeId = store.storeId,
            campaignId = campaign.campaignId,
            deviceAnonId = DeviceIdentityProvider.getDeviceAnonId(),
            message = "Interested in offer",
            createdAt = java.time.Instant.now().toString()
        )

        repository.postEnquiry(request) { result ->
            val currentView = view ?: return@postEnquiry
            result.onSuccess {
                currentView.showToast("Thanks! We'll contact you.")
            }.onFailure { error ->
                currentView.showToast(error.message ?: "Failed to send enquiry")
            }
        }
    }

    private fun formatValidity(startAt: String?, endAt: String?): String {
        if (startAt.isNullOrBlank() || endAt.isNullOrBlank()) {
            return "Validity unavailable"
        }

        val start = formatDate(startAt) ?: startAt
        val end = formatDate(endAt) ?: endAt
        return "Valid: $start - $end"
    }

    private fun formatDate(value: String): String? {
        return try {
            java.time.OffsetDateTime.parse(value)
                .toLocalDate()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy"))
        } catch (_: Exception) {
            null
        }
    }
}
