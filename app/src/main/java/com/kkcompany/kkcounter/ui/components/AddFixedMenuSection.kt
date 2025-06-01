package com.kkcompany.kkcounter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kkcompany.kkcounter.R
import com.kkcompany.kkcounter.ui.model.MenuItemWithRouteString
import com.kkcompany.kkcounter.ui.theme.ButtonNavy

@Composable
fun AddFixedMenuSection(navController: NavController, source: String) {
    val menuItems = listOf(
        MenuItemWithRouteString(R.string.button_manage_table, "manageTableScreen"),
        MenuItemWithRouteString(R.string.button_add_product, "addProductScreen"),
        MenuItemWithRouteString(R.string.button_order_history, "orderHistoryScreen")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            menuItems.forEach { item ->
                RoundedButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    text = stringResource(id = item.titleRes),
                    color = ButtonNavy,
                    height = 48.dp,
                    onClick = { navController.navigate("${item.route}?source=$source") }
                )
            }
        }
        DividerWithFade()
    }
}