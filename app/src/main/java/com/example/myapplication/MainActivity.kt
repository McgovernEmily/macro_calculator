package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sexInput = findViewById<EditText>(R.id.sexInput)
        val ageInput = findViewById<EditText>(R.id.ageInput)
        val heightInput = findViewById<EditText>(R.id.heightInput)
        val weightInput = findViewById<EditText>(R.id.weightInput)
        val calButton = findViewById<Button>(R.id.calbutton)
        val activityGroup = findViewById<RadioGroup>(R.id.activityGroup)

        calButton.setOnClickListener {
            val sex = sexInput.text.toString()
            val age = ageInput.text.toString().toIntOrNull()
            val height = heightInput.text.toString().toDoubleOrNull()
            val weight = weightInput.text.toString().toDoubleOrNull()
            val selectedId = activityGroup.checkedRadioButtonId

            if (age == null || height == null || weight == null || sex.isBlank()){
                return@setOnClickListener
            }

            val findActivity = if (selectedId) {
                R.id.activitySedentary = 1.2
            }

            val macros = calculate(
                weightkg = weight,
                heightcm = height,
                age = age,
                sex = sex
            )
            val intent = Intent(this, ResultActivity::class.java)

            intent.putExtra("calories", macros.calories)
            intent.putExtra("protein", macros.protein)
            intent.putExtra("carbs", macros.carbs)
            intent.putExtra("fats", macros.fats)

            startActivity(intent)
        }



    }

}