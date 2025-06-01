package com.kkcompany.kkcounter.ui.components.ads

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.AdRequest
import com.kkcompany.kkcounter.utils.log.AppLogger
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.LoadAdError

@Composable
fun AdBannerView(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val adView = AdView(context)

            adView.setAdSize(AdSize.BANNER)

            adView.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    AppLogger.i("AdBannerView", "Ad successfully loaded")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    AppLogger.e("AdBannerView", "Ad failed to load: ${error.message}")
                }

                override fun onAdOpened() {
                    AppLogger.i("AdBannerView", "Ad opened (clicked)")
                }

                override fun onAdClosed() {
                    AppLogger.i("AdBannerView", "Ad closed")
                }

                override fun onAdImpression() {
                    AppLogger.i("AdBannerView", "Ad impression recorded")
                }
            }

            adView.loadAd(AdRequest.Builder().build())

            adView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            adView
        }
    )
}