package com.example.offersmvp

class OfferPresenter(
    private val repository: OfferRepository
) : OfferContract.Presenter {

    private var view: OfferContract.View? = null

    override fun attach(view: OfferContract.View) {
        this.view = view
    }

    override fun detach() {
        view = null
    }

    override fun loadOffers() {
        val currentView = view ?: return
        currentView.showLoading()

        val offers = repository.getOffers()

        if (offers.isEmpty()) {
            currentView.showEmptyState()
        } else {
            currentView.showOffers(offers)
        }

        currentView.hideLoading()
    }
}
