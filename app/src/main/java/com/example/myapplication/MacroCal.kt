package com.example.myapplication

import android.R
import kotlin.math.roundToInt

data class Macros(
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fats: Int,
    val activitylevel: Double

)

fun calculate(
    weightkg: Double,
    heightcm: Double,
    age: Int,
    sex: String,
    activitylevel: Int): Macros {

    // Calculating the calories.
    val bmr = if (sex.lowercase() == "male") {
        10 * weightkg + 6.25 * heightcm - 5 * age + 5
    }else{
        10 * weightkg +6.25 * heightcm - 5 * age - 161
    }

    // 1 = sedentary, 2 = light, 3 = moderate, 4 = very active, 5 = super active
    val levels = mapOf(1 to 1.2,
        2 to 1.375, 3 to 1.55,
        4 to 1.725, 5 to 1.9)

    // It will grab the levels, but if there is none then it will do lower end.
    val multiplier = levels[activitylevel] ?: 1.2

    val finalcalories = (bmr * multiplier).roundToInt()

    val protein = (0.8 * weightkg).roundToInt()

    val fat = ((0.3 * finalcalories)/ 9).roundToInt()

    val carbs = ((0.4 * finalcalories)/ 4).roundToInt()

    return Macros(calories = finalcalories, protein = protein, fats = fat, carbs = carbs, activitylevel = multiplier)
}