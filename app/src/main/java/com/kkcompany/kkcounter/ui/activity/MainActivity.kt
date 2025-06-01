package com.kkcompany.kkcounter.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kkcompany.kkcounter.ui.screens.AddProductScreen
import com.kkcompany.kkcounter.ui.screens.MainScreen
import com.kkcompany.kkcounter.ui.screens.ManageTableScreen
import com.kkcompany.kkcounter.ui.screens.OrderHistoryScreen
import com.kkcompany.kkcounter.ui.screens.OrderScreen
import com.kkcompany.kkcounter.ui.screens.SingleScreen
import com.kkcompany.kkcounter.viewmodel.CombinedOrderViewModel
import com.kkcompany.kkcounter.viewmodel.ProductViewModel
import com.kkcompany.kkcounter.viewmodel.TableViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            AppNavHost(navController)
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("singleScreen") { SingleScreen(navController) }
//        composable("multipleScreen") { MultipleScreen(navController) }
        composable("addProductScreen") {
            val productViewModel: ProductViewModel = hiltViewModel()
            AddProductScreen(navController, productViewModel)
        }
        composable("manageTableScreen") {
            val tableViewModel: TableViewModel = hiltViewModel()
            ManageTableScreen(navController, tableViewModel)
        }
        composable("orderHistoryScreen") {
            val combinedOrderViewModel: CombinedOrderViewModel = hiltViewModel()
            OrderHistoryScreen(navController, combinedOrderViewModel)
        }
        composable("order/{tableId}") { backStackEntry ->
            val tableId = backStackEntry.arguments?.getString("tableId")?.toLong() ?: 0L
            OrderScreen(tableId = tableId, onBack = { navController.navigateUp() })
        }
    }
}

