package com.kkcompany.kkcounter.ui.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.kkcompany.kkcounter.ui.model.RectangleItem

@Composable
fun SelectableRectangleCanvas(
    rectangles: List<RectangleItem>,
    selectedId: Long?,
    onClickRectangle: (Long) -> Unit
) {
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val parentWidth = with(density) { constraints.maxWidth.toDp().toPx() }.toInt()
        val parentHeight = with(density) { constraints.maxHeight.toDp().toPx() }.toInt()

        rectangles.forEach { rect ->
            SelectableRectangle(
                item = rect,
                parentWidth = parentWidth,
                parentHeight = parentHeight,
                selectedTableId = selectedId,
                onClick = { onClickRectangle(rect.id) }
            )
        }
    }
}