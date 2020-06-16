package com.ozhayta.adskitdemoapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.ads.AdListener
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.HwAds
import com.huawei.hms.ads.InterstitialAd

class InterstitialAdActivity : AppCompatActivity() {
    private lateinit var interstitialAd: InterstitialAd

    private val TAG = InterstitialAdActivity::class.java.simpleName
    private lateinit var displayRadioGroup: RadioGroup
    private lateinit var loadAdButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial_ad)
        // Initialize the HUAWEI Ads SDK.
        HwAds.init(this)

        displayRadioGroup = findViewById(R.id.display_radio_group)

        loadAdButton = findViewById(R.id.load_ad)
        loadAdButton.setOnClickListener{ loadInterstitialAd() }
    }

    private fun loadInterstitialAd() {
        interstitialAd = InterstitialAd(this)
        interstitialAd.adId = getAdId()
        interstitialAd.adListener = adListener
        val adParam = AdParam.Builder().build()
        interstitialAd.loadAd(adParam)
    }

    private fun getAdId(): String {
        return if (displayRadioGroup.checkedRadioButtonId == R.id.display_image) {
            getString(R.string.image_ad_id)
        } else {
            getString(R.string.video_ad_id)
        }
    }

    private val adListener: AdListener = object : AdListener() {
        override fun onAdLoaded() {
            super.onAdLoaded()
            Toast.makeText(this@InterstitialAdActivity, "Ad loaded", Toast.LENGTH_SHORT).show()
            // Display an interstitial ad.
            showInterstitial()
        }
        override fun onAdFailed(errorCode: Int) {
            Toast.makeText(
                this@InterstitialAdActivity, "Ad load failed with error code: $errorCode",
                Toast.LENGTH_SHORT
            ).show()
            Log.d(TAG, "Ad load failed with error code: $errorCode")
        }
        override fun onAdClosed() {
            super.onAdClosed()
            Log.d(TAG, "onAdClosed")
        }
        override fun onAdClicked() {
            Log.d(TAG, "onAdClicked")
            super.onAdClicked()
        }
        override fun onAdOpened() {
            Log.d(TAG, "onAdOpened")
            super.onAdOpened()
        }
    }

    private fun showInterstitial() { // Display an interstitial ad.
        if (interstitialAd.isLoaded) {
            interstitialAd.show()
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show()
        }
    }

}
