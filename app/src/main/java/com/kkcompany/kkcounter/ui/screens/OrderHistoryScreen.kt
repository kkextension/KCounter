package com.kkcompany.kkcounter.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kkcompany.kkcounter.R
import com.kkcompany.kkcounter.ui.layout.BaseScreenLayout
import com.kkcompany.kkcounter.ui.model.CombinedOrderDisplay
import com.kkcompany.kkcounter.ui.model.OrderDisplayItem
import com.kkcompany.kkcounter.ui.theme.BackgroundCream
import com.kkcompany.kkcounter.utils.toFormattedString
import com.kkcompany.kkcounter.utils.withComma
import com.kkcompany.kkcounter.viewmodel.CombinedOrderViewModel

@Composable
fun OrderHistoryScreen(
    navController: NavController,
    combinedOrderViewModel: CombinedOrderViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val combinedOrders by combinedOrderViewModel.combinedOrders.collectAsState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(Unit) {
        combinedOrderViewModel.loadAllCombinedOrders()
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) { innerPadding ->
        ScreenContent(
            innerPadding = innerPadding,
            navController = navController,
            combinedOrders = combinedOrders,
            context = context
        )
    }

}

@Composable
fun ScreenContent(
    innerPadding : PaddingValues,
    navController: NavController,
    combinedOrders: List<CombinedOrderDisplay>,
    context: Context
) {
    BaseScreenLayout(
        title = stringResource(id = R.string.title_full_order_history),
        onLeftClick = {
            navController.previousBackStackEntry?.let {
                navController.popBackStack()
            } ?: navController.navigate("main")
        },
        onRightClick = { navController.navigate("main") }
    ) {
        LazyColumn {
            items(combinedOrders) { order ->
                OrderCard(order = order, context = context)
            }
        }
    }
}

@Composable
fun OrderCard(order: CombinedOrderDisplay, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 6.dp,
        backgroundColor = if (order.completedAt == null) BackgroundCream else Color.White
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = if (order.completedAt == null)
                    context.getString(R.string.textField_label_table_order_in_progress)
                else
                    context.getString(R.string.textField_label_table_completed_order),
                fontWeight = FontWeight.Bold
            )
            Text("${context.getString(R.string.textField_label_table_name)}: ${order.tableName}")
            Text("${context.getString(R.string.textField_label_table_total)}: ${order.totalPrice.withComma()}")
            order.completedAt?.let {
                Text("${context.getString(R.string.normal_time)}: ${it.toFormattedString()}")
            }

            Spacer(modifier = Modifier.height(4.dp))

            order.items.forEach {
                OrderItemText(item = it)
            }
        }
    }
}

@Composable
fun OrderItemText(item: OrderDisplayItem) {
    Text("- ${item.productName} x${item.quantity} (${item.price.withComma()})")
}