package com.kkcompany.kkcounter.utils.log

import com.google.firebase.crashlytics.FirebaseCrashlytics

class ReleaseLogger : Logger {
    override fun d(tag: String, message: String) { /* No-op */ }
    override fun i(tag: String, message: String) { /* No-op */ }
    override fun w(tag: String, message: String) { /* No-op */ }
    override fun e(tag: String, message: String, throwable: Throwable?) {
        sendToCrashlytics(tag, message, throwable)
    }

    private fun sendToCrashlytics(tag: String, message: String, throwable: Throwable?) {
        FirebaseCrashlytics.getInstance().log("$tag: $message")
        throwable?.let { FirebaseCrashlytics.getInstance().recordException(it) }
    }
}