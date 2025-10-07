package com.example.greenhouse.model

data class Greenhouse(
    val id: String = "",
    val name: String = "",
    val temperature: Float = 0f,
    val sections: List<Section> = emptyList()
)