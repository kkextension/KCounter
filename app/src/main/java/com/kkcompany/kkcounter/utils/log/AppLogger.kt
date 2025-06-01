package com.kkcompany.kkcounter.utils.log

object AppLogger {
    private lateinit var logger: Logger

    fun init(isDebug: Boolean) {
        logger = if (isDebug) DebugLogger() else ReleaseLogger()
    }

    fun d(tag: String, message: String) = logger.d(tag, message)
    fun i(tag: String, message: String) = logger.i(tag, message)
    fun w(tag: String, message: String) = logger.w(tag, message)
    fun e(tag: String, message: String, throwable: Throwable? = null) =
        logger.e(tag, message, throwable)
}