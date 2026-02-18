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

    override fun loadOffers(uuid: String, major: Int, minor: Int) {
        val currentView = view ?: return
        currentView.showLoading()

        repository.getActiveOffer(uuid, major, minor) { offer ->
            val activeView = view ?: return@getActiveOffer

            if (offer == null) {
                activeView.showEmptyState()
            } else {
                activeView.showOffers(listOf(offer))
            }

            activeView.hideLoading()
        }
    }
}
