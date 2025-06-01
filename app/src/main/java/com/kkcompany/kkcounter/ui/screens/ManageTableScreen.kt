package com.kkcompany.kkcounter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kkcompany.kkcounter.R
import com.kkcompany.kkcounter.ui.components.AddDynamicMenuSection
import com.kkcompany.kkcounter.ui.components.SelectableRectangleCanvas
import com.kkcompany.kkcounter.ui.layout.BaseScreenLayout
import com.kkcompany.kkcounter.ui.model.MenuItemWithClickFunction
import com.kkcompany.kkcounter.ui.model.enums.TableScreenMode.ADDING
import com.kkcompany.kkcounter.ui.model.enums.TableScreenMode.DEFAULT
import com.kkcompany.kkcounter.ui.model.enums.TableScreenMode.EDITING
import com.kkcompany.kkcounter.ui.model.enums.TableScreenMode.SELECTED
import com.kkcompany.kkcounter.ui.theme.BackgroundGray
import com.kkcompany.kkcounter.viewmodel.TableViewModel

@Composable
fun ManageTableScreen(navController: NavController, tableViewModel: TableViewModel) {
    val previousRoute = navController.previousBackStackEntry?.destination?.route
    val context = LocalContext.current
    val showSnackbar = tableViewModel.showSnackbar
    val scaffoldState = rememberScaffoldState()

    val source = remember {
        navController.currentBackStackEntry?.arguments?.getString("source") ?: "unknown"
    }

    LaunchedEffect(showSnackbar) {
        tableViewModel.fetchTables()
        if (showSnackbar) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = context.getString(R.string.message_add_table)
            )
            tableViewModel.snackbarShown()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) { innerPadding ->
        BaseScreenLayout(
            title = stringResource(id = R.string.title_manage_table),
            onLeftClick = { handleHeaderButton(navController, previousRoute) },
            onRightClick = { navController.navigate("main") }
        ) {
            ScreenContent(modifier = Modifier.fillMaxSize().padding(innerPadding), navController = navController, tableViewModel = tableViewModel)
        }
    }
}

private fun handleHeaderButton(navController: NavController, previousRoute: String?) {
    if (previousRoute != null) {
        navController.popBackStack()
    } else {
        navController.navigate("main")
    }
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    tableViewModel: TableViewModel
) {
    val rectangles = tableViewModel.rectangles

    Column(modifier = modifier) {
        AddDynamicMenuSection(
            listOf(
                MenuItemWithClickFunction(
                    R.string.dynamic_add_menu_table,
                    enabled = tableViewModel.screenMode == DEFAULT || tableViewModel.screenMode == SELECTED
                ) {
                    tableViewModel.startAddingTable()
                },
                MenuItemWithClickFunction(
                    R.string.dynamic_edit_menu_table,
                    enabled = tableViewModel.screenMode == SELECTED
                ) {
                    tableViewModel.startEditingTable()
                },
                MenuItemWithClickFunction(
                    R.string.dynamic_delete_menu_table,
                    enabled = tableViewModel.screenMode == SELECTED
                ) {
                    tableViewModel.deleteSelectedTable()
                }
            )
        )

        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(BackgroundGray)
        ) {
            when (tableViewModel.screenMode) {
                ADDING, EDITING -> {
                    AddOrEditTableScreen(navController, tableViewModel)
                }

                else -> SelectableRectangleCanvas(
                    rectangles = rectangles,
                    selectedId = tableViewModel.selectedTableId,
                    onClickRectangle = { id -> tableViewModel.selectTable(id) }
                )
            }
        }
    }
}