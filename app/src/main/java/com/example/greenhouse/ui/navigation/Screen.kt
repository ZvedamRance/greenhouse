package com.example.greenhouse.ui.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object GreenhouseDetail : Screen("greenhouse_detail/{id}") {
        fun createRoute(id: String) = "greenhouse_detail/$id"
    }
    object AddGreenhouse : Screen("add_greenhouse")
    object AddSection : Screen("add_section/{greenhouseId}") {
        fun createRoute(greenhouseId: String) = "add_section/$greenhouseId"
    }
    object SectionDetail : Screen("section_detail/{greenhouseId}/{sectionId}") {
        fun createRoute(greenhouseId: String, sectionId: String) = "section_detail/$greenhouseId/$sectionId"
    }
    object EditSection : Screen("edit_section/{greenhouseId}/{sectionId}") {
        fun createRoute(greenhouseId: String, sectionId: String) = "edit_section/$greenhouseId/$sectionId"
    }
}
