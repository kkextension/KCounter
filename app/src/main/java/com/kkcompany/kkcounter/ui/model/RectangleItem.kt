package com.kkcompany.kkcounter.ui.model

data class RectangleItem(
    val id: Long,
    val name: String = "",
    val floor: Int = 1,
    val xRatio: Float = 0f,
    val yRatio: Float = 0f,
    val widthRatio: Float = 50f,
    val heightRatio: Float = 50f,
    val description: String = "",
    val isSelected: Boolean = false,
    val isEditable: Boolean = false
)
