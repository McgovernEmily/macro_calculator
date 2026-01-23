package com.example.myapplication

import android.R
import kotlin.math.roundToInt

data class Macros(
    val calories: Int,
    val proteinGrams: Int,
    val carbGrams: Int,
    val fatGrams: Int,
    val activityLevel: Double
)


fun calculate(
    weightkg: Double,
    heightcm: Double,
    age: Int,
    sex: String,
    activitylevel: Int
): Macros {

    // Calculating the calories.
    val bmr = if (sex.lowercase() == "male") {
        10 * weightkg + 6.25 * heightcm - 5 * age + 5
    } else {
        10 * weightkg + 6.25 * heightcm - 5 * age - 161
    }

    // 1 = sedentary, 2 = light, 3 = moderate, 4 = very active, 5 = super active
    val levels = mapOf(
        1 to 1.2,
        2 to 1.375, 3 to 1.55,
        4 to 1.725, 5 to 1.9
    )

    // It will grab the levels, but if there is none then it will do lower end.
    val multiplier = levels[activitylevel] ?: 1.2

    val finalcalories = (bmr * multiplier).roundToInt()

    // g per kg
    val proteinGrams = (1.8 * weightkg).roundToInt()
    val proteinCalories = proteinGrams * 4


    // 25% of calories
    val fatCalories = (0.25 * finalcalories).roundToInt()
    val fatGrams = (fatCalories / 9.0).roundToInt()

    val remainingCalories = (finalcalories - proteinCalories - fatCalories).coerceAtLeast(0)
    val carbGrams = (remainingCalories / 4.0).roundToInt()

    return Macros(
        calories = finalcalories,
        proteinGrams = proteinGrams,
        fatGrams = fatGrams,
        carbGrams = carbGrams,
        activityLevel = multiplier
    )
}