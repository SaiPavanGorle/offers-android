package com.example.offersmvp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), OfferContract.View {

    private lateinit var presenter: OfferContract.Presenter
    private lateinit var progressBar: ProgressBar
    private lateinit var offerText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        offerText = findViewById(R.id.offerText)

        presenter = OfferPresenter(OfferRepository())
        presenter.attach(this)
        presenter.loadOffers(
            uuid = AppConfig.DEMO_UUID,
            major = AppConfig.DEMO_MAJOR,
            minor = AppConfig.DEMO_MINOR
        )
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        offerText.visibility = View.GONE
    }

    override fun showOffers(offers: List<Offer>) {
        val content = offers.joinToString(separator = "\n\n") {
            "${it.title}\n${it.description}"
        }
        offerText.text = content
        offerText.visibility = View.VISIBLE
    }

    override fun showEmptyState() {
        offerText.text = getString(R.string.empty_offers)
        offerText.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }
}
