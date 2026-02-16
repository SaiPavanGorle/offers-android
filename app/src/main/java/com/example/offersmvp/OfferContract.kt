package com.example.offersmvp

interface OfferContract {
    interface View {
        fun showLoading()
        fun showOffers(offers: List<Offer>)
        fun showEmptyState()
        fun hideLoading()
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun loadOffers()
    }
}
