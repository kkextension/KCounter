package com.kkcompany.kkcounter.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kkcompany.kkcounter.ui.model.OrderDisplayItem
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun parseItemsJson(json: String): List<OrderDisplayItem> {
    val gson = Gson()
    val type = object : TypeToken<List<OrderDisplayItem>>() {}.type
    return gson.fromJson(json, type)
}

fun Int.withComma(): String = DecimalFormat("#,###").format(this)

fun Date.toFormattedString(): String {
    val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}