package com.kkcompany.kkcounter.ui.screens


import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.kkcompany.kkcounter.R
import com.kkcompany.kkcounter.ui.components.AddFixedMenuSection
import com.kkcompany.kkcounter.ui.layout.BaseScreenLayout

@Composable
fun MultipleScreen(navController: NavController) {
    BaseScreenLayout(
        title = stringResource(id = R.string.title_Multiple),
        onLeftClick = { navController.navigate("main") },
        onRightClick = { navController.navigate("main") }
    ) {
        AddFixedMenuSection(navController, source = "multiple")
    }
}