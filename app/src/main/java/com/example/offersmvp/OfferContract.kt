package com.example.offersmvp

interface OfferContract {
    interface View {
        fun showLoading()
        fun showCampaign(campaign: CampaignDto, store: StoreDto)
        fun showEmptyState()
        fun hideLoading()
        fun showEnquirySuccess()
        fun showEnquiryFailure(message: String)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun loadOffers(uuid: String, major: Int, minor: Int)
        fun submitEnquiry(message: String)
    }
}
