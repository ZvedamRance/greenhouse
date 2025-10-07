package com.example.greenhouse.ui.navigation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.greenhouse.ui.screen.AddGreenhouseScreen
import com.example.greenhouse.ui.screen.AddSectionScreen
import com.example.greenhouse.ui.screen.GreenhouseDetailScreen
import com.example.greenhouse.ui.screen.MainScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            MainScreen(navController)
        }
        composable(Screen.GreenhouseList.route) {
            GreenhouseListScreen(navController)
        }
        composable(
            Screen.GreenhouseDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            GreenhouseDetailScreen(navController, id)
        }
        composable(Screen.AddGreenhouse.route) {
            AddGreenhouseScreen(navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController)
        }
        composable(
            route = "add_section/{greenhouseId}",
            arguments = listOf(navArgument("greenhouseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val greenhouseId = backStackEntry.arguments?.getString("greenhouseId") ?: ""
            AddSectionScreen(navController, greenhouseId)
        }
    }
}

@Composable
fun SettingsScreen(x0: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Add Greenhouse Screen")
    }
}

@Composable
fun GreenhouseListScreen(x0: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Add Greenhouse Screen")
    }
}

