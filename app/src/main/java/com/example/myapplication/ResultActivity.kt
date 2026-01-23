package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        val resultText = findViewById<TextView>(R.id.results)

        val calories = intent.getIntExtra("calories", 0)
        val proteinGrams = intent.getIntExtra("proteinGrams", 0)
        val fatsGrams = intent.getIntExtra("fatsGrams", 0)
        val carbsGrams = intent.getIntExtra("carbsGrams", 0)

        resultText.text = """
            Calories: $calories 
            Protein: ${proteinGrams}g
            Carbs: ${carbsGrams}g
            Fats: ${fatsGrams}g
        """.trimIndent()
    }
}