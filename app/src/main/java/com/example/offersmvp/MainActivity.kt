package com.example.offersmvp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), OfferContract.View {

    companion object {
        private const val IBEACON_UUID = "F7826DA6-4FA2-4E98-8024-BC5B71E0893E"
        private const val IBEACON_MAJOR = 1001
        private const val IBEACON_MINOR = 1
    }

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
            uuid = IBEACON_UUID,
            major = IBEACON_MAJOR,
            minor = IBEACON_MINOR
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
