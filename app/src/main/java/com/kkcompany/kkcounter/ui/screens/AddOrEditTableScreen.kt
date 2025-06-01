package com.kkcompany.kkcounter.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.kkcompany.kkcounter.R
import com.kkcompany.kkcounter.ui.components.TableCanvas
import com.kkcompany.kkcounter.ui.components.TableForm
import com.kkcompany.kkcounter.viewmodel.TableViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
    fun AddOrEditTableScreen(navController: NavController, viewModel: TableViewModel) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarVisible = viewModel.showSnackbar

    LaunchedEffect(snackbarVisible) {
        if (snackbarVisible) {
            scaffoldState.snackbarHostState.showSnackbar(message = context.getString(R.string.snackBar_table_added))
            viewModel.snackbarShown()
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) { innerPadding ->
        ScreenContent(
            context = context,
            viewModel = viewModel,
            scaffoldState = scaffoldState,
            coroutineScope = coroutineScope,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun ScreenContent(
    context: Context,
    viewModel: TableViewModel,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    val rectangles = viewModel.rectangles

    Row(
        modifier = modifier
            .fillMaxSize()
    ) {
        TableForm(context, viewModel, scaffoldState, coroutineScope)
        TableCanvas(
            rectangles = rectangles,
            enabled = true,
            editingId = viewModel.selectedTableId,
            onUpdate = { id, x, y, w, h ->
                viewModel.updateRectangle(id, x, y, w, h)
            }
        )
    }
}