package com.ozhayta.adskitdemoapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adsList: ArrayList<String>
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // creating and setting adsList
        adsList = ArrayList()
        adsList.add(getString(R.string.reward_ad))
        adsList.add(getString(R.string.interstitial_ad))
        adsList.add(getString(R.string.splash_ad))
        adsList.add(getString(R.string.banner_ad))
        adsList.add(getString(R.string.native_ad))

        // binding listView and setting adapter
        listView = findViewById(R.id.listView)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, adsList)
        listView.adapter = arrayAdapter

        // setting listener for user clicks
        listView.setOnItemClickListener { parent, view, position, id ->
            getIntent(adsList[position])?.let {startActivity(it)}
        }
    }

    private fun getIntent(to:String) : Intent? {
        return when (to) {
            getString(R.string.reward_ad) -> Intent(this, RewardAdActivity::class.java)
            getString(R.string.interstitial_ad) -> Intent(this, InterstitialAdActivity::class.java)
            getString(R.string.banner_ad) -> Intent(this, BannerActivity::class.java)
            getString(R.string.native_ad) -> Intent(this, NativeAdActivity::class.java)

            getString(R.string.splash_ad) -> {
                finish()
                Intent(this, SplashActivity::class.java)
            }
            else ->  null
        }
    }



}
