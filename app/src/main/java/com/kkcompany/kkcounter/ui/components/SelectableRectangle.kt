package com.kkcompany.kkcounter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kkcompany.kkcounter.ui.model.RectangleItem
import com.kkcompany.kkcounter.utils.log.AppLogger

@Composable
fun SelectableRectangle(
    item: RectangleItem,
    parentWidth: Int,
    parentHeight: Int,
    selectedTableId: Long?,
    onClick: () -> Unit
) {
    val isSelected = item.id == selectedTableId
    val density = LocalDensity.current

    val offsetX = item.xRatio * parentWidth
    val offsetY = item.yRatio * parentHeight
    val width = item.widthRatio * parentWidth
    val height = item.heightRatio * parentHeight

    AppLogger.d("Selectable", "id: ${item.id}, isSelected: $isSelected")

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .size(with(density) { width.toDp() }, with(density) { height.toDp() })
            .background(
                color = if (isSelected) Color.Red.copy(alpha = 0.3f) else Color.Blue.copy(alpha = 0.5f),
                shape = RoundedCornerShape(6.dp)
            )
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) Color.Red else Color.DarkGray,
                shape = RoundedCornerShape(6.dp)
            )
            .clickable { onClick() }
    ){
        Text(
            text = item.name,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(4.dp)
        )
    }
}