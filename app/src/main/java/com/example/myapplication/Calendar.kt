package com.example.myapplication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.ProgressBar

class Calendar : AppCompatActivity() {

    private lateinit var calorieProgress: ProgressBar
    private lateinit var proteinProgress: ProgressBar
    private lateinit var carbsProgress: ProgressBar
    private lateinit var fatsProgress: ProgressBar

    // Connecting to the activity calendar XML
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_calendar)

        // Finding the calendar in the XML
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calorieProgress = findViewById<ProgressBar>(R.id.calorieProgress)
        proteinProgress = findViewById<ProgressBar>(R.id.proteinProgress)
        fatsProgress = findViewById<ProgressBar>(R.id.fatsProgress)
        carbsProgress = findViewById<ProgressBar>(R.id.carbsProgress)

        // Creating variables.
        val calories = intent.getIntExtra("calories", 0)
        val protein = intent.getIntExtra("protein", 0)
        val fats = intent.getIntExtra("fats", 0)
        val carbs = intent.getIntExtra("carbs", 0)

        calorieProgress.max = calories
        proteinProgress.max = protein
        carbsProgress.max = carbs
        fatsProgress.max = fats


        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->

            val datekey = "$year - ${month + 1} - $dayOfMonth"

            // Update progress bars
            checkDayMacros(datekey)

            // Open food log screen
            val intent = Intent(this, FoodLogActivity::class.java)
            intent.putExtra("date", datekey)
            startActivity(intent)
        }
    }


    // This will read the meals and add all the foods together
    private fun checkDayMacros(dateKey: String) {

        calorieProgress.progress = 0
        proteinProgress.progress = 0
        carbsProgress.progress = 0
        fatsProgress.progress = 0

        val db = FirebaseFirestore.getInstance()

        val meals = listOf("Breakfast", "Lunch", "Snack", "Dinner")

        var totalCalories = 0
        var totalProtein = 0
        var totalCarbs = 0
        var totalFats = 0

        var completedQueries = 0

        for (meal in meals) {

            db.collection("foodLogs")
                .document(dateKey)
                .collection(meal)
                .get()
                .addOnSuccessListener { result ->

                    for (doc in result) {

                        val food = doc.toObject(FoodLog::class.java)

                        totalCalories += food.foodCalories
                        totalProtein += food.foodProtein
                        totalCarbs += food.foodCarb
                        totalFats += food.foodFat
                    }

                    completedQueries++

                    if (completedQueries == meals.size) {

                        calorieProgress.progress = minOf(totalCalories, calorieProgress.max)
                        proteinProgress.progress = minOf(totalProtein, proteinProgress.max)
                        carbsProgress.progress = minOf(totalCarbs, carbsProgress.max)
                        fatsProgress.progress = minOf(totalFats, fatsProgress.max)

                    }
                }
        }
    }

}