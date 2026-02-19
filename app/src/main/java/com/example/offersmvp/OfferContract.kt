package com.example.offersmvp

interface OfferContract {
    interface View {
        fun showLoading()
        fun showCampaign(title: String, description: String, validText: String, websiteUrl: String?)
        fun showError(message: String)
        fun hideLoading()
        fun openUrl(url: String)
        fun showToast(message: String)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun loadOffers(uuid: String, major: Int, minor: Int)
        fun onOpenWebsiteClicked()
        fun onInterestedClicked()
    }
}
