package com.example.greenhouse.ui.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object GreenhouseList : Screen("greenhouse_list")
    object GreenhouseDetail : Screen("greenhouse_detail/{id}") {
        fun createRoute(id: String) = "greenhouse_detail/$id"
    }
    object AddGreenhouse : Screen("add_greenhouse")
    object Settings : Screen("settings")
}
