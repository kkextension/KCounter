package com.kkcompany.kkcounter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kkcompany.kkcounter.ui.model.RectangleItem
import com.kkcompany.kkcounter.ui.theme.BackgroundGray

@Composable
fun RectangleCanvas(
    rectangles: List<RectangleItem>,
    onUpdateRectangle: (id: Long, x: Float, y: Float, width: Float, height: Float) -> Unit,
    enabled: Boolean = true,
    editingId: Long? = null,
    onClickRectangle: (id: Long) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        rectangles.forEach { rect ->
            DraggableResizableRectangle(
                item = rect,
                onUpdate = { x, y, w, h ->
                    onUpdateRectangle(rect.id, x, y, w, h)
                },
                enabled = enabled,
                editingId = editingId,
                onClick = {
                    onClickRectangle(rect.id)
                }
            )
        }
    }
}