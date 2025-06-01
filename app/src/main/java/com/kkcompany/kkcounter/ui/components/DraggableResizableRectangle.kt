package com.kkcompany.kkcounter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kkcompany.kkcounter.ui.model.RectangleItem

private const val MIN_RECT_SIZE = 50f

@Composable
fun DraggableResizableRectangle(
    item: RectangleItem,
    onUpdate: (Float, Float, Float, Float) -> Unit,
    onClick: (() -> Unit)? = null,
    editingId: Long? = null,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val parentWidth = constraints.maxWidth.toFloat()
        val parentHeight = constraints.maxHeight.toFloat()

        var offsetX by remember { mutableStateOf(item.xRatio * parentWidth) }
        var offsetY by remember { mutableStateOf(item.yRatio * parentHeight) }
        var width by remember { mutableStateOf(item.widthRatio * parentWidth) }
        var height by remember { mutableStateOf(item.heightRatio * parentHeight) }

        fun notifyUpdate() {
            onUpdate(
                offsetX / parentWidth,
                offsetY / parentHeight,
                width / parentWidth,
                height / parentHeight
            )
        }

        val density = LocalDensity.current
        val isEditable = enabled && (editingId == item.id)

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
                .size(with(density) { width.toDp() }, with(density) { height.toDp() })
                .background(Color.Blue.copy(alpha = 0.5f))
                .pointerInput(isEditable) {
                    if (isEditable) {
                        detectDragGestures(
                            onDragStart = {},
                            onDragEnd = {},
                            onDragCancel = {}
                        ) { change, dragAmount ->
                            change.consume()
                            offsetX = (offsetX + dragAmount.x).coerceIn(0f, parentWidth - width)
                            offsetY = (offsetY + dragAmount.y).coerceIn(0f, parentHeight - height)
                            notifyUpdate()
                        }
                    }
                }
                .then(
                    if (!isEditable && onClick != null) Modifier.clickable { onClick() }
                    else Modifier
                )
        ) {
            Text(
                text = item.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(4.dp)
            )
            if (isEditable) {
                ResizeHandle(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onResize = { dx, dy ->
                        width = (width + dx).coerceIn(MIN_RECT_SIZE, parentWidth - offsetX)
                        height = (height + dy).coerceIn(MIN_RECT_SIZE, parentHeight - offsetY)
                        notifyUpdate()
                    }
                )
            }
        }
    }
}