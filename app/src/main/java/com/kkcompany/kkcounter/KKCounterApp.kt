package com.kkcompany.kkcounter

import android.app.Application
import com.kkcompany.kkcounter.utils.log.AppLogger
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class KKCounterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppLogger.init(BuildConfig.DEBUG)

        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            MobileAds.initialize(this@KKCounterApp) {}
        }
    }
}