package com.example.offersmvp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.load
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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

    private var currentCampaign: CampaignDto? = null
    private var currentStore: StoreDto? = null

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
        interestedButton.setOnClickListener { showEnquiryDialog() }

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

    override fun showCampaign(campaign: CampaignDto, store: StoreDto) {
        currentCampaign = campaign
        currentStore = store

        titleText.text = campaign.title
        descriptionText.text = campaign.description
        validityText.text = formatValidity(campaign.startAt, campaign.endAt)

        bannerImage.load(campaign.bannerUrl) {
            crossfade(true)
        }

        contentScroll.visibility = View.VISIBLE
        emptyText.visibility = View.GONE
    }

    override fun showEmptyState() {
        contentScroll.visibility = View.GONE
        emptyText.text = getString(R.string.empty_offers)
        emptyText.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showEnquirySuccess() {
        Toast.makeText(this, getString(R.string.enquiry_success), Toast.LENGTH_SHORT).show()
    }

    override fun showEnquiryFailure(message: String) {
        Toast.makeText(this, message.ifBlank { getString(R.string.enquiry_failure) }, Toast.LENGTH_SHORT).show()
    }

    private fun openWebsite() {
        val url = currentCampaign?.websiteUrl ?: currentStore?.websiteUrl
        if (url.isNullOrBlank()) {
            Toast.makeText(this, getString(R.string.website_unavailable), Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun showEnquiryDialog() {
        val input = EditText(this).apply {
            hint = getString(R.string.enquiry_message_hint)
        }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.im_interested))
            .setView(input)
            .setPositiveButton(getString(R.string.submit)) { _, _ ->
                presenter.submitEnquiry(input.text.toString())
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun formatValidity(startAt: String?, endAt: String?): String {
        if (startAt.isNullOrBlank() || endAt.isNullOrBlank()) {
            return getString(R.string.validity_unknown)
        }

        val start = parseDateTime(startAt)
        val end = parseDateTime(endAt)

        return if (start != null && end != null) {
            getString(R.string.validity_range, start, end)
        } else {
            getString(R.string.validity_range, startAt, endAt)
        }
    }

    private fun parseDateTime(value: String): String? {
        return try {
            val localDate = OffsetDateTime.parse(value)
                .atZoneSameInstant(ZoneId.systemDefault())
                .toLocalDate()
            localDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        } catch (_: Exception) {
            null
        }
    }
}
