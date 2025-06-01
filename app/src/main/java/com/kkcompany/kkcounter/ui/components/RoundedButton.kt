@file:OptIn(ExperimentalMaterialApi::class)

package com.kkcompany.kkcounter.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kkcompany.kkcounter.ui.theme.ButtonNavy
import com.kkcompany.kkcounter.ui.theme.CustomShapes

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    height: Dp = Dp.Unspecified,
    onClick: () -> Unit,
    enabled: Boolean = true,
    textStyle: TextStyle = TextStyle(fontSize = 15.sp),
    shape: Shape = CustomShapes.medium
) {
    val backgroundColor = if (enabled) color else color.copy(alpha = 0.3f)
    val contentColor = if (enabled) Color.White else Color.Gray

    val finalModifier = if (height != Dp.Unspecified) {
        modifier
            .height(height)
    } else {
        modifier
    }

    Surface(
        modifier = finalModifier
            .clip(shape)
            .border(1.dp, ButtonNavy, shape),
        color = backgroundColor,
        contentColor = contentColor,
        onClick = onClick,
        enabled = enabled
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = text, style = textStyle)
        }
    }
}