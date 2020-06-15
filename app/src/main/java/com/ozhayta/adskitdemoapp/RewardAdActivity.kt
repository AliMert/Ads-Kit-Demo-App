package com.ozhayta.adskitdemoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.HwAds
import com.huawei.hms.ads.reward.Reward
import com.huawei.hms.ads.reward.RewardAd
import com.huawei.hms.ads.reward.RewardAdLoadListener
import com.huawei.hms.ads.reward.RewardAdStatusListener


class RewardAdActivity : AppCompatActivity() {

    private  var rewardAd: RewardAd? =null
    private lateinit var  interstitialAdsButton : Button
    private lateinit var watchAdButton: Button
    private lateinit var scoreView: TextView

    private var score = 0
    private val defaultScore = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward_ad)


        // Initialize the HUAWEI Ads SDK.
        HwAds.init(this)

        // Load a rewarded ad.
        loadRewardAd()

        // Load the button for watching a rewarded ad.
        loadWatchVideoButton();

        // Load a score view.
        loadScoreView();

    }

    private val rewardAdLoadListener: RewardAdLoadListener = object : RewardAdLoadListener() {
        override fun onRewardAdFailedToLoad(errorCode: Int) {
            Toast.makeText(
                this@RewardAdActivity,
                "onRewardAdFailedToLoad errorCode is :$errorCode",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onRewardedLoaded() {
            Toast.makeText(this@RewardAdActivity, "onRewardedLoaded", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createRewardAd() {
        rewardAd = RewardAd(this@RewardAdActivity, getString(R.string.reward_ad_id))
    }


    private fun loadRewardAd() {
        if (rewardAd == null) {
            createRewardAd()
        }

        rewardAd!!.loadAd(AdParam.Builder().build(), rewardAdLoadListener)
    }

    /**
     * Load the button for watching a rewarded ad.
     */
    private fun loadWatchVideoButton() {
        watchAdButton = findViewById(R.id.show_video_button)
        watchAdButton.setOnClickListener { rewardAdShow() }
    }

    private fun loadScoreView() {
        scoreView = findViewById(R.id.coin_count_text)
        scoreView.text = "Score:$score"
    }

    /**
     * Display a rewarded ad.
     */
    private fun rewardAdShow(): Unit {
        if (rewardAd!!.isLoaded) {
            rewardAd!!.show(this@RewardAdActivity, object : RewardAdStatusListener() {
                override fun onRewardAdClosed() {
                    loadRewardAd()
                }

                override fun onRewardAdFailedToShow(errorCode: Int) {
                    Toast.makeText(
                        this@RewardAdActivity,
                        "onRewardAdFailedToShow errorCode is :$errorCode",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onRewardAdOpened() {
                    Toast.makeText(this@RewardAdActivity, "onRewardAdOpened", Toast.LENGTH_SHORT).show()
                }

                override fun onRewarded(reward: Reward) { // You are advised to grant a reward immediately and at the same time, check whether the reward takes effect on the server.
// If no reward information is configured, grant a reward based on the actual scenario.
                    val addScore =
                        if (reward.amount == 0) defaultScore else reward.amount
                    Toast.makeText(
                        this@RewardAdActivity,
                        "Watch video show finished, add $addScore scores",
                        Toast.LENGTH_SHORT
                    ).show()
                    addScore(addScore)
                    loadRewardAd()
                }
            })
        }

    }

    private fun addScore(addScore: Int) {
        score += addScore
        val scoreString = "Score:$score"
        scoreView.text = scoreString
    }

}
