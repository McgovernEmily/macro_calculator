package com.example.myapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.DateKeyListener
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class Calendar : AppCompatActivity() {

    // Connecting to the activity calendar XML
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_calendar)

        // Finding the calendar in the XML
        val calendarView = findViewById<CalendarView>(R.id.calendarView)


        // Creating variables.
        val calories = intent.getIntExtra("calories", 0)
        val protein = intent.getIntExtra("protein", 0)
        val fats = intent.getIntExtra("fats", 0)
        val carbs = intent.getIntExtra("carbs", 0)


        calendarView.setOnDateChangeListener {_, year, month, dayOfMonth ->
            val datekey = "$year-${month + 1}-$dayOfMonth"

            showGoalDialog(datekey, calories, protein, fats, carbs)

        }
    }

    // Will show the dialog when clicking the day on the calendar.
    private fun showGoalDialog(
        datekey: String,
        calories: Int,
        protein: Int,
        fats: Int,
        carbs: Int
    ){
        AlertDialog.Builder(this)
            .setTitle("Did you meet your macro goal?")
            .setMessage(
                "Calories: $calories\n" +
                "Protein: $protein g\n" +
                "Carbs: $carbs g\n" +
                "fats: $fats g"
            )
            // If yes is selected it will make the goal true, if no is selected it will make it false.
            .setPositiveButton("YES") { _, _ ->
                saveDayResult(datekey, true)
                Toast.makeText(this, "Saved for $datekey", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("NO") {_, _ ->
                saveDayResult(datekey, false)
                Toast.makeText(this, "Saved for $datekey", Toast.LENGTH_SHORT).show()
            }
            .show()
    }



    private fun saveDayResult(dateKey: String, metGoal: Boolean) {
        val pref = getSharedPreferences("nutrition_calendar", MODE_PRIVATE)
        pref.edit()
            .putBoolean(dateKey, metGoal)
            .apply()
    }

}