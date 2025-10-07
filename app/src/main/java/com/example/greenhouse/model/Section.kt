package com.example.greenhouse.model

data class Section (
    val id: String = "",
    val name: String = "",
    val plant: String = "",
    val moisture: Float = 0f,
    val water: Float = 0f
)