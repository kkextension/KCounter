package com.kkcompany.kkcounter.ui.model

import com.google.gson.annotations.SerializedName

data class OrderDisplayItem(
    @SerializedName("id") val id: Long,
    @SerializedName("tableId") val tableId: Long,
    @SerializedName("productId") val productId: Long,
    @SerializedName("tableName") val tableName: String,
    @SerializedName("productName") val productName: String,
    @SerializedName("price") val price: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("orderedAt") val orderedAt: Long
)