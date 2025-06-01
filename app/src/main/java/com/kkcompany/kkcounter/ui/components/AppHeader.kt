package com.kkcompany.kkcounter.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kkcompany.kkcounter.R

private val ButtonSize = 24.dp
private val HeaderHeight = 22.dp
private val HeaderPadding = 18.dp

@Composable
fun AppHeader(
    title: String,
    onLeftClick: (() -> Unit)? = null,
    onRightClick: (() -> Unit)? = null
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(HeaderPadding)
                .height(HeaderHeight)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HeaderIconButton(onClick = onLeftClick, icon = Icons.AutoMirrored.Filled.ArrowBack, contentDesc = R.string.header_back)
                HeaderIconButton(onClick = onRightClick, icon = Icons.Default.Home, contentDesc = R.string.header_home)
            }

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Divider(color = Color.Gray, thickness = 1.dp)
    }
}

@Composable
private fun HeaderIconButton(
    onClick: (() -> Unit)?,
    icon: ImageVector,
    @StringRes contentDesc: Int
) {
    if (onClick != null) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(ButtonSize)
                .padding(0.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(id = contentDesc),
                modifier = Modifier.fillMaxSize()
            )
        }
    } else {
        Spacer(modifier = Modifier.size(ButtonSize))
    }
}