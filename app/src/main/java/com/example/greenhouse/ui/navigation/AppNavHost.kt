package com.example.greenhouse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.greenhouse.ui.screen.AddGreenhouseScreen
import com.example.greenhouse.ui.screen.AddSectionScreen
import com.example.greenhouse.ui.screen.EditSectionScreen
import com.example.greenhouse.ui.screen.GreenhouseDetailScreen
import com.example.greenhouse.ui.screen.MainScreen
import com.example.greenhouse.ui.screen.SectionDetailScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {

        // Hlavní obrazovka
        composable(Screen.Main.route) {
            MainScreen(navController)
        }

        // Detail skleníku
        composable(
            Screen.GreenhouseDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            GreenhouseDetailScreen(navController, id)
        }

        // Přidání skleníku
        composable(Screen.AddGreenhouse.route) {
            AddGreenhouseScreen(navController)
        }

        // Přidání sekce
        composable(
            route = Screen.AddSection.route,
            arguments = listOf(navArgument("greenhouseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val greenhouseId = backStackEntry.arguments?.getString("greenhouseId") ?: ""
            AddSectionScreen(navController, greenhouseId)
        }

        // Detail sekce
        composable(
            route = Screen.SectionDetail.route,
            arguments = listOf(
                navArgument("greenhouseId") { type = NavType.StringType },
                navArgument("sectionId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val greenhouseId = backStackEntry.arguments?.getString("greenhouseId") ?: ""
            val sectionId = backStackEntry.arguments?.getString("sectionId") ?: ""
            SectionDetailScreen(navController, greenhouseId, sectionId)
        }

        // Editace sekce
        composable(
            route = Screen.EditSection.route,
            arguments = listOf(
                navArgument("greenhouseId") { type = NavType.StringType },
                navArgument("sectionId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val greenhouseId = backStackEntry.arguments?.getString("greenhouseId") ?: ""
            val sectionId = backStackEntry.arguments?.getString("sectionId") ?: ""
            EditSectionScreen(navController, greenhouseId, sectionId)
        }
    }
}
