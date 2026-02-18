package com.example.offersmvp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), OfferContract.View {

    private lateinit var presenter: OfferContract.Presenter
    private lateinit var progressBar: ProgressBar
    private lateinit var contentScroll: ScrollView
    private lateinit var emptyText: TextView
    private lateinit var bannerImage: ImageView
    private lateinit var titleText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var validityText: TextView
    private lateinit var openWebsiteButton: Button
    private lateinit var interestedButton: Button

    private var currentWebsiteUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DeviceIdentityProvider.initialize(applicationContext)

        progressBar = findViewById(R.id.progressBar)
        contentScroll = findViewById(R.id.contentScroll)
        emptyText = findViewById(R.id.emptyText)
        bannerImage = findViewById(R.id.bannerImage)
        titleText = findViewById(R.id.titleText)
        descriptionText = findViewById(R.id.descriptionText)
        validityText = findViewById(R.id.validityText)
        openWebsiteButton = findViewById(R.id.openWebsiteButton)
        interestedButton = findViewById(R.id.interestedButton)

        openWebsiteButton.setOnClickListener { openWebsite() }
        interestedButton.setOnClickListener { presenter.submitEnquiry() }

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
        contentScroll.visibility = View.GONE
        emptyText.visibility = View.GONE
    }

    override fun showCampaign(title: String, description: String, validText: String, websiteUrl: String?) {
        currentWebsiteUrl = websiteUrl

        titleText.text = title
        descriptionText.text = description
        validityText.text = validText

        bannerImage.visibility = View.GONE

        contentScroll.visibility = View.VISIBLE
        emptyText.visibility = View.GONE
    }

    override fun showError(message: String) {
        contentScroll.visibility = View.GONE
        emptyText.text = message
        emptyText.visibility = View.VISIBLE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showEnquirySent() {
        Toast.makeText(this, getString(R.string.enquiry_success), Toast.LENGTH_SHORT).show()
    }

    private fun openWebsite() {
        val url = currentWebsiteUrl
        if (url.isNullOrBlank()) {
            Toast.makeText(this, getString(R.string.website_unavailable), Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
