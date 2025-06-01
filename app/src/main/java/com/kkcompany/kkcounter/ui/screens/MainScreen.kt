package com.kkcompany.kkcounter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kkcompany.kkcounter.R
import com.kkcompany.kkcounter.ui.components.RoundedButton
import com.kkcompany.kkcounter.ui.layout.BaseScreenLayout
import com.kkcompany.kkcounter.ui.theme.ButtonNavy

@Composable
fun MainScreen(navController: NavController) {
    BaseScreen(
        backgroundColor = Color.White,
        darkIcons = true
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            BaseScreenLayout(
                title = stringResource(id = R.string.title_select_main_menu),
                onLeftClick = null,
                onRightClick = null
            ) {
                ScreenContent(navController)
            }
        }
    }
}

@Composable
fun ScreenContent(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RoundedButton(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.button_single_floor),
            height = 180.dp,
            color = ButtonNavy,
            onClick = { navController.navigate("singleScreen") },
            enabled = true
        )

        RoundedButton(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.button_multiple_floor),
            height = 180.dp,
            color = ButtonNavy,
            onClick = { navController.navigate("multipleScreen") },
            enabled = false
        )
    }
}

