package com.ozhayta.adskitdemoapp


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.ads.AdListener
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.BannerAdSize
import com.huawei.hms.ads.HwAds
import com.huawei.hms.ads.banner.BannerView


class BannerActivity : AppCompatActivity() {
    private val TAG = BannerActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)

        // Initialize the HUAWEI Ads SDK.
        HwAds.init(this)

        val topBannerView = findViewById<BannerView>(R.id.hw_banner_view_top)
        topBannerView.adId = getString(R.string.ad_id_banner)
        topBannerView.bannerAdSize = BannerAdSize.BANNER_SIZE_320_50

        val bottomBannerView = findViewById<BannerView>(R.id.hw_banner_view_bottom)
        bottomBannerView.adId = getString(R.string.ad_id_banner)
        bottomBannerView.bannerAdSize = BannerAdSize.BANNER_SIZE_360_57

        val adParam = AdParam.Builder().build()

        topBannerView.loadAd(adParam)
        bottomBannerView.loadAd(adParam)

        topBannerView.adListener = getAdListener()
        bottomBannerView.adListener = getAdListener()

        Toast.makeText(this@BannerActivity, "Banner Ads are requested", Toast.LENGTH_SHORT).show()
    }

    private fun getAdListener(): AdListener {
        return object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                Toast.makeText(this@BannerActivity, "Ad loaded", Toast.LENGTH_SHORT).show()
            }

            override fun onAdFailed(errorCode: Int) {
                Toast.makeText(
                    this@BannerActivity, "Ad load failed with error code: $errorCode",
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
    }
}
