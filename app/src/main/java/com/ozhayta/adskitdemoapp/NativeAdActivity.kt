package com.ozhayta.adskitdemoapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.ads.AdListener
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.HwAds
import com.huawei.hms.ads.VideoOperator.VideoLifecycleListener
import com.huawei.hms.ads.nativead.*


class NativeAdActivity : AppCompatActivity() {

    private lateinit var adScrollView: ScrollView
    private lateinit var smallNativeView: NativeView
    private lateinit var largeNativeView: NativeView
    private lateinit var videoNativeView: NativeView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_ad)

        // binding views
        smallNativeView = findViewById(R.id.native_ad_small)
        largeNativeView = findViewById(R.id.native_ad_large)
        videoNativeView = findViewById(R.id.native_ad_video)
        adScrollView = findViewById(R.id.scroll_view_ad)

        // Initialize the HUAWEI Ads SDK.
        HwAds.init(this)

        // load ads based on ad id to native ad views
        loadAd(getString(R.string.ad_id_native_video), videoNativeView)
        loadAd(getString(R.string.ad_id_native_small), smallNativeView)
        loadAd(getString(R.string.ad_id_native), largeNativeView)
    }



    private fun loadAd(adId: String, nativeView: NativeView) {
        val builder = NativeAdLoader.Builder(this, adId)
        builder.setNativeAdLoadedListener { nativeAd ->
            // Call this method when an ad is successfully loaded.
            toastMessage(getString(R.string.status_load_ad_success))
            // Display native ad.
            showNativeAd(nativeAd, nativeView)
            nativeAd.setDislikeAdListener {
                // Call this method when an ad is closed.
                toastMessage(getString(R.string.ad_is_closed))
            }
        }

        builder.setAdListener(object : AdListener() {
            override fun onAdFailed(errorCode: Int) { // Call this method when an ad fails to be loaded.
                toastMessage(getString(R.string.status_load_ad_fail) + errorCode)
            }
        })

        val adConfiguration = NativeAdConfiguration.Builder()
            .setChoicesPosition(NativeAdConfiguration.ChoicesPosition.BOTTOM_RIGHT) // Set custom attributes.
            .build()

        val nativeAdLoader = builder.setNativeAdOptions(adConfiguration).build()
        nativeAdLoader.loadAd(AdParam.Builder().build())
        toastMessage(getString(R.string.status_ad_loading))
    }



    private fun showNativeAd(nativeAd: NativeAd, nativeView: NativeView) {
        // Register and populate a native ad material view.
        initNativeAdView(nativeAd, nativeView)
        adScrollView.addView(nativeView)  // Add NativeView to the app UI.
    }



    private fun initNativeAdView(nativeAd: NativeAd, nativeView: NativeView) {
        // Register a native ad material view.
        nativeView.titleView = nativeView.findViewById(R.id.ad_title)
        nativeView.mediaView = nativeView.findViewById(R.id.ad_media)
        nativeView.adSourceView = nativeView.findViewById(R.id.ad_source)
        nativeView.callToActionView = nativeView.findViewById(R.id.ad_call_to_action)

        // Populate a native ad material view.
        (nativeView.titleView as TextView).text = nativeAd.title
        nativeView.mediaView.setMediaContent(nativeAd.mediaContent)
        if (null != nativeAd.adSource) {
            (nativeView.adSourceView as TextView).text = nativeAd.adSource
        }
        nativeView.adSourceView.visibility = if (null != nativeAd.adSource) View.VISIBLE else View.INVISIBLE
        if (null != nativeAd.callToAction) {
            (nativeView.callToActionView as Button).text = nativeAd.callToAction
        }
        nativeView.callToActionView.visibility = if (null != nativeAd.callToAction) View.VISIBLE else View.INVISIBLE

        // Check whether a native ad contains video materials.
        if (nativeAd.videoOperator.hasVideo()) { // Add a video lifecycle event listener.
            nativeAd.videoOperator.videoLifecycleListener = videoLifecycleListener
        }

        nativeView.setNativeAd(nativeAd) // Register a native ad object.
    }



    private val videoLifecycleListener: VideoLifecycleListener = object : VideoLifecycleListener() {
        override fun onVideoStart() {
            toastMessage(getString(R.string.status_play_start))
        }
        override fun onVideoPlay() {
            toastMessage(getString(R.string.status_playing))
        }
        override fun onVideoEnd() { // If there is a video, load a new native ad only after video playback is complete.
            toastMessage(getString(R.string.status_play_end))
        }
    }

    private fun toastMessage(text: String) {
        text.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
    }
}
