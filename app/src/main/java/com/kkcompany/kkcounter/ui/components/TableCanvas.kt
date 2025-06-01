package com.kkcompany.kkcounter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kkcompany.kkcounter.ui.model.RectangleItem
import com.kkcompany.kkcounter.ui.theme.BackgroundGray

@Composable
fun RowScope.TableCanvas(
    rectangles: List<RectangleItem>,
    editingId: Long?,
    enabled: Boolean = true,
    onUpdate: (id: Long, xRatio: Float, yRatio: Float, widthRatio: Float, heightRatio: Float) -> Unit,
    onClickRectangle: (id: Long) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .weight(7f)
            .fillMaxHeight()
            .padding(16.dp)
            .background(BackgroundGray)
    ) {
        RectangleCanvas(
            rectangles = rectangles,
            onUpdateRectangle = onUpdate,
            editingId = editingId,
            enabled = enabled,
            onClickRectangle = onClickRectangle
        )
    }
}