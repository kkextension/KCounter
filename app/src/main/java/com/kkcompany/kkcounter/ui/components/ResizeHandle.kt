package com.kkcompany.kkcounter.ui.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kkcompany.kkcounter.R

@Composable
fun ResizeHandle(
    modifier: Modifier,
    onResize: (dx: Float, dy: Float) -> Unit
) {
    val resizeSensitivity = 0.7f
    val resizeHandleSize = 20.dp

    Box(
        modifier = modifier
            .size(resizeHandleSize)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    onResize(dragAmount.x * resizeSensitivity, dragAmount.y * resizeSensitivity)
                }
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_resize_handle),
            contentDescription = "Resize Handle",
            modifier = Modifier.fillMaxSize(),
            tint = Color.Unspecified
        )
    }
}