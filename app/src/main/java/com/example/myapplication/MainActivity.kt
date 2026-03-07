package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity(){


    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    // Connecting to the XML.
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        // The user/
        val user = auth.currentUser

        // Creating variable to the layout.
        val sexInput = findViewById<EditText>(R.id.sexInput)
        val ageInput = findViewById<EditText>(R.id.ageInput)
        val heightInput = findViewById<EditText>(R.id.heightInput)
        val weightInput = findViewById<EditText>(R.id.weightInput)
        val calButton = findViewById<Button>(R.id.calbutton)
        val activityGroup = findViewById<RadioGroup>(R.id.activityGroup)

        // Checking if the user is logged in already
        if (user != null) {
            checkMacroProfile(user.uid)
        }


        // When the the edittext is selected the user can type.
        calButton.setOnClickListener {
            val sex = sexInput.text.toString()
            val age = ageInput.text.toString().toIntOrNull()
            val height = heightInput.text.toString().toDoubleOrNull()
            val weight = weightInput.text.toString().toDoubleOrNull()
            val selectedId = activityGroup.checkedRadioButtonId

            if (age == null || height == null || weight == null || sex.isBlank()) {
                return@setOnClickListener
            }

            // Finding the activity level
            val activityLevel = when (selectedId) {
                R.id.activitySedentary -> 1
                R.id.activityLight -> 2
                R.id.activityModerate -> 3
                R.id.activityActive -> 4
                R.id.activityExtra -> 5
                else -> 1
            }

            // Aligning the variable in macrocal to mainactivity variables.
            val macros = calculate(
                weightkg = weight,
                heightcm = height,
                age = age,
                sex = sex,
                activitylevel = activityLevel
            )

            // The user
            val user = auth.currentUser

            if (user != null) {

                val data = hashMapOf(
                    "calories" to macros.calories,
                    "protein" to macros.proteinGrams,
                    "carbs" to macros.carbGrams,
                    "fats" to macros.fatGrams
                )

                db.collection("users")
                    .document(user.uid)
                    .set(data)
            }

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("calories", macros.calories)
            intent.putExtra("proteinGrams", macros.proteinGrams)
            intent.putExtra("carbsGrams", macros.carbGrams)
            intent.putExtra("fatsGrams", macros.fatGrams)

            startActivity(intent)
        }



    }

    // Checking if the user has a macro profile
    private fun checkMacroProfile(uid: String) {

        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                if (document.exists()) {

                    val intent = Intent(this, Calendar::class.java)

                    intent.putExtra("calories", document.getLong("calories")?.toInt() ?: 0)
                    intent.putExtra("protein", document.getLong("protein")?.toInt() ?: 0)
                    intent.putExtra("carbs", document.getLong("carbs")?.toInt() ?: 0)
                    intent.putExtra("fats", document.getLong("fats")?.toInt() ?: 0)

                    startActivity(intent)
                    finish()

                }
            }
    }

}