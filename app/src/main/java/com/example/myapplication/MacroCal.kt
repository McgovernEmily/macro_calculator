package com.example.myapplication

import android.R
import kotlin.math.roundToInt

data class Macros(
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fats: Int,
    val activitylevel

)

fun calculate(
    weightkg: Double,
    heightcm: Double,
    age: Int,
    sex: String,
    activitylevel: Double): Macros {

    // Calculating the calories.
    val bmr = if (sex.lowercase() == "male") {
        10 * weightkg + 6.25 * heightcm - 5 * age + 5
    }else{
        10 * weightkg +6.25 * heightcm - 5 * age - 161
    }

    val activitySel = if(activitylevel)

    val finalcalories = (bmr * 1.2).roundToInt()

    val protein = (0.8 * weightkg).roundToInt()

    val fat = ((0.3 * finalcalories)/ 9).roundToInt()

    val carbs = ((0.4 * finalcalories)/ 4).roundToInt()

    return Macros(calories = finalcalories, protein = protein, fats = fat, carbs = carbs)
}