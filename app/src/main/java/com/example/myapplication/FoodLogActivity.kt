package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class FoodLogActivity : AppCompatActivity() {

    private lateinit var dateKey: String
    private lateinit var foodListText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_log)

        foodListText = findViewById(R.id.foodListText)

        val backButton = findViewById<ImageView>(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }
        // Get date from Calendar activity
        dateKey = intent.getStringExtra("date") ?: ""

        // Show date at top of screen
        val dateText = findViewById<TextView>(R.id.dateText)
        dateText.text = "Food Log for $dateKey"

        // Buttons
        val breakfastButton = findViewById<Button>(R.id.breakfastButton)
        val lunchButton = findViewById<Button>(R.id.lunchButton)
        val snackButton = findViewById<Button>(R.id.snackButton)
        val dinnerButton = findViewById<Button>(R.id.dinnerButton)

        // Button listeners
        breakfastButton.setOnClickListener { openAddFood("Breakfast") }
        lunchButton.setOnClickListener { openAddFood("Lunch") }
        snackButton.setOnClickListener { openAddFood("Snack") }
        dinnerButton.setOnClickListener { openAddFood("Dinner") }
    }

    override fun onResume() {
        super.onResume()
        loadFoods()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // Opens activity to add food
    private fun openAddFood(meal: String) {

        val intent = Intent(this, AddFoodActivity::class.java)

        intent.putExtra("meal", meal)
        intent.putExtra("date", dateKey)

        startActivity(intent)
    }

    // Load foods from Firebase
    private fun loadFoods() {


        val db = FirebaseFirestore.getInstance()

        foodListText.text = ""

        // All meal categories
        val meals = listOf("Breakfast", "Lunch", "Snack", "Dinner")

        for (meal in meals) {

            db.collection("foodLogs")
                .document(dateKey)
                .collection(meal)
                .get()
                .addOnSuccessListener { result ->

                    for (doc in result) {

                        val food = doc.toObject(FoodLog::class.java)

                        val currentText = foodListText.text.toString()

                        foodListText.text =
                            currentText + "\n${food.mealType}: ${food.foodName} (${food.foodCalories} cal)"
                    }
                }
        }
    }
}