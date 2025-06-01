package com.kkcompany.kkcounter.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kkcompany.kkcounter.R
import com.kkcompany.kkcounter.data.db.entities.ProductEntity
import com.kkcompany.kkcounter.ui.components.RoundedButton
import com.kkcompany.kkcounter.ui.layout.BaseScreenLayout
import com.kkcompany.kkcounter.ui.model.enums.SortColumn
import com.kkcompany.kkcounter.ui.state.ProductUiState
import com.kkcompany.kkcounter.ui.theme.ButtonNavy
import com.kkcompany.kkcounter.utils.withComma
import com.kkcompany.kkcounter.viewmodel.ProductViewModel

@Composable
fun AddProductScreen(navController: NavController, productViewModel: ProductViewModel) {
    val previousRoute = navController.previousBackStackEntry?.destination?.route
    val products by productViewModel.products.collectAsState()
    val uiState = productViewModel.uiState
    val context = LocalContext.current
    val showSnackbar = productViewModel.showSnackbar

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = context.getString(R.string.message_add_product)
            )
            productViewModel.snackbarShown()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) { innerPadding ->
        BaseScreenLayout(
            title = stringResource(id = R.string.title_add_product),
            onLeftClick = { handleHeaderButton(navController, previousRoute) },
            onRightClick = { navController.navigate("main") }
        ) {
            ScreenContent(uiState = uiState, products = products, innerPadding = innerPadding, productViewModel = productViewModel)
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
private fun ScreenContent(
    uiState: ProductUiState,
    products: List<ProductEntity>,
    innerPadding: PaddingValues,
    productViewModel: ProductViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(top = 16.dp)
    ) {
        ProductFormSection(uiState, productViewModel, Modifier.weight(3f))
        ProductListSection(products, productViewModel, Modifier.weight(7f))
    }
}

@Composable
private fun ProductFormSection(
    uiState: ProductUiState,
    productViewModel: ProductViewModel,
    modifier: Modifier
) {
    Column(modifier = modifier.padding(start = 16.dp)) {
        Text(
            text = stringResource(id = R.string.textField_label_menu_name),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = uiState.name,
            onValueChange = { productViewModel.updateUiState(name = it) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.DarkGray,
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(id = R.string.textField_label_menu_price),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = uiState.price,
            onValueChange = { productViewModel.updateUiState(price = it) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.DarkGray,
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(id = R.string.textField_label_menu_description),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = uiState.description,
            onValueChange = { productViewModel.updateUiState(description = it) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.DarkGray,
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        RoundedButton(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = stringResource(id = R.string.button_add_product),
            color = ButtonNavy,
            height = 48.dp,
            onClick = { productViewModel.addProduct() }
        )
    }
}

@Composable
private fun ProductListSection(
    products: List<ProductEntity>,
    productViewModel: ProductViewModel,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        ProductListHeader(productViewModel)
        Divider()
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(products, key = { it.id }) { product ->
                ProductItem(product = product)
            }
        }
    }
}

@Composable
private fun ProductListHeader(viewModel: ProductViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        SortableHeader(
            title = stringResource(id = R.string.textField_label_menu_name),
            column = SortColumn.NAME,
            currentSortColumn = viewModel.sortColumn,
            isAscending = viewModel.isAscending,
            enable = true,
            modifier = Modifier.weight(3.5f)
        ) {
            viewModel.sortProducts(SortColumn.NAME, viewModel.sortColumn != SortColumn.NAME || !viewModel.isAscending)
        }

        SortableHeader(
            title = stringResource(id = R.string.textField_label_menu_price),
            column = SortColumn.PRICE,
            currentSortColumn = viewModel.sortColumn,
            isAscending = viewModel.isAscending,
            enable = true,
            modifier = Modifier.weight(3.5f)
        ) {
            viewModel.sortProducts(SortColumn.PRICE, viewModel.sortColumn != SortColumn.PRICE || !viewModel.isAscending)
        }

        SortableHeader(
            title = stringResource(id = R.string.textField_label_menu_description),
            column = SortColumn.DESCRIPTION,
            currentSortColumn = viewModel.sortColumn,
            isAscending = viewModel.isAscending,
            enable = false,
            modifier = Modifier.weight(3f)
        ) {
            viewModel.sortProducts(SortColumn.DESCRIPTION, false)
        }
    }
}

@Composable
fun SortableHeader(
    title: String,
    column: SortColumn,
    currentSortColumn: SortColumn,
    isAscending: Boolean,
    enable: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val isCurrent = column == currentSortColumn
    val iconAlpha = if (isCurrent) 1f else 0f
    val iconVector = if (isAscending) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward

    Row(
        modifier = modifier
            .clickable(enabled = enable) { onClick() }
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        Icon(
            imageVector = iconVector,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .alpha(iconAlpha)
        )
    }
}

@Composable
fun ProductItem(product: ProductEntity) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = product.name,
                modifier = Modifier.weight(3.5f).padding(start = 4.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
            Text(
                text = product.price.withComma(),
                modifier = Modifier.weight(3.5f).padding(start = 4.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
            Text(
                text = product.description,
                modifier = Modifier.weight(3f).padding(start = 4.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
        }
        Divider()
    }
}
