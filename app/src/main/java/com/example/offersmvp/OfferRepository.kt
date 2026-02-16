package com.example.offersmvp

class OfferRepository {
    fun getOffers(): List<Offer> {
        return listOf(
            Offer("1", "10% Off", "Save 10% on your first order"),
            Offer("2", "Free Shipping", "Get free shipping this weekend"),
            Offer("3", "Bundle Deal", "Buy 2 items and get 1 free")
        )
    }
}
