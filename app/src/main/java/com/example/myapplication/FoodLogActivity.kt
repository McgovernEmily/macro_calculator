package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FoodLogActivity : AppCompatActivity() {

    private lateinit var dateKey: String

    // Edit the food
    private lateinit var recyclerView: RecyclerView
    private val foodList = mutableListOf<FoodLog>()
    private lateinit var editing: Foodedit

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_log)

        val backButton = findViewById<ImageView>(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }
        // Get date from Calendar activity
        dateKey = intent.getStringExtra("date") ?: ""

        // Show date at top of screen
        val dateText = findViewById<TextView>(R.id.dateText)
        dateText.text = "Food Log for $dateKey"

        // Setup RecyclerView
        recyclerView = findViewById(R.id.foodList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        editing = Foodedit(
            foodList,
            onEdit = { food -> editFood(food) },
            onDelete = { food -> deleteFood(food) }
        )

        recyclerView.adapter = editing

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

    // Editing the foods
    private fun editFood(food: FoodLog) {

        val intent = Intent(this, AddFoodActivity::class.java)

        intent.putExtra("meal", food.mealType)
        intent.putExtra("date", dateKey)
        intent.putExtra("foodId", food.id)
        intent.putExtra("foodName", food.foodName)
        intent.putExtra("calories", food.foodCalories)
        intent.putExtra("protein", food.foodProtein)
        intent.putExtra("carbs", food.foodCarb)
        intent.putExtra("fats", food.foodFat)

        startActivity(intent)

    }


    // Load foods from Firebase
    private fun loadFoods() {


        val db = FirebaseFirestore.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        foodList.clear()

        // All meal categories
        val meals = listOf("Breakfast", "Lunch", "Snack", "Dinner")

        for (meal in meals) {

            db.collection("users")
                .document(uid)
                .collection("foodLogs")
                .document(dateKey)
                .collection(meal)
                .get()
                .addOnSuccessListener { result ->

                    for (doc in result) {

                        val food = doc.toObject(FoodLog::class.java)
                        food.id = doc.id
                        foodList.add(food)
                    }

                    editing.notifyDataSetChanged()
                }
        }
    }

    private fun deleteFood(food: FoodLog) {

        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .document(uid)
            .collection("foodLogs")
            .document(dateKey)
            .collection(food.mealType)
            .document(food.id)
            .delete()
            .addOnSuccessListener {
                loadFoods()
            }

    }

}