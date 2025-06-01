package com.kkcompany.kkcounter.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkcompany.kkcounter.R
import com.kkcompany.kkcounter.data.db.entities.ProductEntity
import com.kkcompany.kkcounter.ui.components.RoundedButton
import com.kkcompany.kkcounter.ui.layout.BaseScreenLayout
import com.kkcompany.kkcounter.ui.model.OrderDisplayItem
import com.kkcompany.kkcounter.ui.state.OrderDisplayUiState
import com.kkcompany.kkcounter.ui.theme.ButtonNavy
import com.kkcompany.kkcounter.utils.withComma
import com.kkcompany.kkcounter.viewmodel.OrderViewModel
import com.kkcompany.kkcounter.viewmodel.ProductViewModel

@Composable
fun OrderScreen(
    tableId: Long,
    onBack: () -> Unit,
    orderViewModel: OrderViewModel = hiltViewModel(),
    productViewModel: ProductViewModel = hiltViewModel()
) {
    val orderDisplayState by orderViewModel.orderDisplayUiState.collectAsState()
    val products by productViewModel.products.collectAsState()
    val context = LocalContext.current
    val showSnackbar = orderViewModel.showSnackbar
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(tableId) {
        orderViewModel.loadOrdersForTable(tableId)
        productViewModel.fetchProducts()
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) { innerPadding ->

        ScreenContent(
            innerPadding = innerPadding,
            tableId = tableId,
            orderDisplayState = orderDisplayState,
            products = products,
            context = context,
            onBack = onBack,
            onOrderDelete = { order, id -> orderViewModel.deleteOrder(order, id) },
            onProductClick = { product -> orderViewModel.addOrUpdateOrder(tableId, product) },
            onComplete = { orderViewModel.completeOrder(tableId) }
        )
    }
}

@Composable
fun ScreenContent(
    innerPadding: PaddingValues,
    tableId: Long,
    orderDisplayState: OrderDisplayUiState,
    products: List<ProductEntity>,
    context: Context,
    onBack: () -> Unit,
    onOrderDelete: (OrderDisplayItem, Long) -> Unit,
    onProductClick: (ProductEntity) -> Unit,
    onComplete: () -> Unit
) {
    BaseScreenLayout(
        title = context.getString(R.string.title_table_order_management, tableId),
        onLeftClick = onBack,
        onRightClick = {},
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            OrderListSection(
                orders = orderDisplayState.orders,
                totalPrice = orderDisplayState.totalPrice,
                context = context,
                onDelete = onOrderDelete,
                onComplete = onComplete,
                modifier = Modifier.weight(3f)
            )

            ProductGridSection(
                products = products,
                onProductClick = onProductClick,
                modifier = Modifier.weight(7f)
            )
        }
    }
}

@Composable
fun OrderListSection(
    orders: List<OrderDisplayItem>,
    totalPrice: Int,
    context: Context,
    onDelete: (OrderDisplayItem, Long) -> Unit,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showCompleteDialog by remember { mutableStateOf(false) }

    if (showCompleteDialog) {
        AlertDialog(
            onDismissRequest = { showCompleteDialog = false },
            title = { Text(text = stringResource(id = R.string.dialog_complete_payment_title)) },
            text = { Text(text = stringResource(id = R.string.dialog_complete_payment_content)) },
            confirmButton = {
                TextButton(onClick = {
                    onComplete()
                    showCompleteDialog = false
                }) {
                    Text(text = stringResource(id = R.string.dialog_normal_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showCompleteDialog = false }) {
                    Text(text = stringResource(id = R.string.dialog_normal_cancel))
                }
            }
        )
    }

    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            items(orders) { order ->
                OrderItemCard(order = order, context = context) {
                    onDelete(order, order.id)
                }
            }
        }

        Text(
            text = "${context.getString(R.string.textField_label_table_total)}:\n${totalPrice.withComma()}",
            style = MaterialTheme.typography.h6,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        RoundedButton(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.button_complete_payment),
            color = ButtonNavy,
            height = 48.dp,
            onClick = { showCompleteDialog = true },
            enabled = true
        )
    }
}

@Composable
fun OrderItemCard(
    order: OrderDisplayItem,
    context: Context,
    onDeleteOne: (OrderDisplayItem) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(id = R.string.dialog_order_cancel_title)) },
            text = { Text(text = stringResource(id = R.string.dialog_order_cancel_content)) },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteOne(order)
                    showDialog = false
                }) {
                    Text(text = stringResource(id = R.string.dialog_normal_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = stringResource(id = R.string.dialog_normal_cancel))
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { showDialog = true },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(order.productName)
            Text("${context.getString(R.string.textField_label_quantity)}: ${order.quantity}")
        }
    }
}

@Composable
fun ProductGridSection(
    products: List<ProductEntity>,
    onProductClick: (ProductEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .padding(8.dp)
            .background(Color.White)
    ) {
        items(products) { product ->
            ProductCard(product = product, onClick = { onProductClick(product) })
        }
    }
}

@Composable
fun ProductCard(product: ProductEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp).height(80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(product.name, fontWeight = FontWeight.Bold)
            Text(product.price.withComma())
            Text(product.description, fontSize = 12.sp)
        }
    }
}