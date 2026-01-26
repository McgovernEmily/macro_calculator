package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

        val calories = intent.getIntExtra("calories", 0)
        val proteinGrams = intent.getIntExtra("proteinGrams", 0)
        val fatsGrams = intent.getIntExtra("fatsGrams", 0)
        val carbsGrams = intent.getIntExtra("carbsGrams", 0)

        // Finding the id in the xml file and assigning it.
        findViewById<TextView>(R.id.caloriesText).text = "Calories: $calories"
        findViewById<TextView>(R.id.proteinText).text = "Protein: $proteinGrams g"
        findViewById<TextView>(R.id.carbsText).text = "Carbs: $carbsGrams g"
        findViewById<TextView>(R.id.fatsText).text = "Fats: $fatsGrams g"


        val calButton = findViewById<Button>(R.id.calendarButton)

        calButton.setOnClickListener {
            val intent = Intent(this, Calendar::class.java)
            intent.putExtra("calories", calories)
            intent.putExtra("protein", proteinGrams)
            intent.putExtra("carbs", carbsGrams)
            intent.putExtra("fats", fatsGrams)
            startActivity(intent)
        }

    }
}