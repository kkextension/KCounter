package com.kkcompany.kkcounter.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kkcompany.kkcounter.R
import com.kkcompany.kkcounter.ui.components.AddFixedMenuSection
import com.kkcompany.kkcounter.ui.components.TableCanvas
import com.kkcompany.kkcounter.ui.layout.BaseScreenLayout
import com.kkcompany.kkcounter.viewmodel.TableViewModel

@Composable
fun SingleScreen(navController: NavController, tableViewModel: TableViewModel = hiltViewModel()) {
    BaseScreen(
        backgroundColor = Color.White,
        darkIcons = true
    ) {
        LaunchedEffect(Unit) {
            tableViewModel.fetchTables()
        }
        ScreenContent(
            navController = navController,
            tableViewModel = tableViewModel,
            onNavigateToMain = { navController.navigate("main") },
            )
    }
}

@Composable
fun ScreenContent(
    navController: NavController,
    tableViewModel: TableViewModel,
    onNavigateToMain: () -> Unit,
) {

    val rectangles = tableViewModel.rectangles

    BaseScreenLayout(
        title = stringResource(id = R.string.title_single),
        onLeftClick = onNavigateToMain,
        onRightClick = onNavigateToMain
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AddFixedMenuSection(
                navController = navController,
                source = "single"
            )

            Row {
                TableCanvas(
                    rectangles = rectangles,
                    enabled = false,
                    editingId = null,
                    onUpdate = { id, x, y, w, h ->
                        tableViewModel.updateRectangle(id, x, y, w, h)
                    },
                    onClickRectangle = { tableId ->
                        navController.navigate("order/$tableId")
                    }
                )
            }
        }
    }
}
