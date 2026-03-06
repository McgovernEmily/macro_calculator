package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AddFoodActivity : AppCompatActivity() {

    private lateinit var dateKey: String
    private lateinit var mealType: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        val backButton = findViewById<ImageView>(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }
        dateKey = intent.getStringExtra("date") ?: ""
        mealType = intent.getStringExtra("meal") ?: ""

        val nameInput = findViewById<EditText>(R.id.foodNameInput)
        val caloriesInput = findViewById<EditText>(R.id.caloriesInput)
        val proteinInput = findViewById<EditText>(R.id.proteinInput)
        val carbsInput = findViewById<EditText>(R.id.carbsInput)
        val fatInput = findViewById<EditText>(R.id.fatInput)

        val saveButton = findViewById<Button>(R.id.saveFoodButton)

        saveButton.setOnClickListener {

            val food = FoodLog(
                foodName = nameInput.text.toString(),
                foodCalories = caloriesInput.text.toString().toInt(),
                foodProtein = proteinInput.text.toString().toInt(),
                foodCarb = carbsInput.text.toString().toInt(),
                foodFat = fatInput.text.toString().toInt(),
                mealType = mealType
            )

            saveFood(food)
            finish()
        }
    }

    private fun saveFood(food: FoodLog) {

        val db = FirebaseFirestore.getInstance()

        db.collection("foodLogs")
            .document(dateKey)
            .collection(mealType)
            .add(food)
    }
}