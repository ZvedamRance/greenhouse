package com.example.greenhouse.model

data class Sensor(
    val id: String = "",
    val type: String = "",
    val value: Double = 0.0,
    val unit: String = "",
    val timestamp: Long = System.currentTimeMillis()
)