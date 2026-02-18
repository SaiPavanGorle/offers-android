package com.example.offersmvp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OfferPresenter(
    private val repository: OfferRepository
) : OfferContract.Presenter {

    private var view: OfferContract.View? = null
    private val presenterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun attach(view: OfferContract.View) {
        this.view = view
    }

    override fun detach() {
        presenterScope.cancel()
        view = null
    }

    override fun loadOffers(uuid: String, major: Int, minor: Int) {
        val currentView = view ?: return
        presenterScope.launch {
            currentView.showLoading()

            try {
                val offers = withContext(Dispatchers.IO) {
                    repository.getOffers(uuid, major, minor)
                }

                if (offers.isEmpty()) {
                    currentView.showEmptyState()
                } else {
                    currentView.showOffers(offers)
                }
            } finally {
                currentView.hideLoading()
            }
        }
    }
}
