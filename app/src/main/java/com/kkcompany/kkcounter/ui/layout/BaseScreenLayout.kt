package com.kkcompany.kkcounter.ui.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kkcompany.kkcounter.ui.components.AppHeader
import com.kkcompany.kkcounter.ui.components.ads.AdBannerView

@Composable
fun BaseScreenLayout(
    title: String,
    onLeftClick: (() -> Unit)? = null,
    onRightClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AdBannerView(
            modifier = Modifier.fillMaxWidth().height(50.dp)
        )

        AppHeader(title = title, onLeftClick = onLeftClick, onRightClick = onRightClick)

        Box(modifier = Modifier.weight(1f)) {
            content()
        }
    }
}