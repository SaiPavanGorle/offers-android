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

        repository.getStoreWithCampaign(uuid, major, minor) { store, campaign ->
            val activeView = view ?: return@getStoreWithCampaign

            if (store == null || campaign == null) {
                activeView.showEmptyState()
            } else {
                currentStore = store
                currentCampaign = campaign
                activeView.showCampaign(campaign, store)
            }

            activeView.hideLoading()
        }
    }

    override fun submitEnquiry(message: String) {
        val activeView = view ?: return
        val store = currentStore
        val campaign = currentCampaign

        if (store == null || campaign == null || message.isBlank()) {
            activeView.showEnquiryFailure("Invalid enquiry details")
            return
        }

        val request = EnquiryRequest(
            storeId = store.storeId,
            campaignId = campaign.campaignId,
            deviceAnonId = DeviceIdentityProvider.getDeviceAnonId(),
            message = message.trim()
        )

        repository.submitEnquiry(request) { success ->
            val currentView = view ?: return@submitEnquiry
            if (success) {
                currentView.showEnquirySuccess()
            } else {
                currentView.showEnquiryFailure("Request failed")
            }
        }
    }
}
