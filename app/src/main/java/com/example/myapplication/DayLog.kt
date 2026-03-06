package com.example.myapplication

data class DayLog(
    val date: String = "",
    val foods: MutableList<FoodLog> = mutableListOf()
)