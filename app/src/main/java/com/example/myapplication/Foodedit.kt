package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Foodedit (
    private val foods: List<FoodLog>,
    private val onEdit: (FoodLog) -> Unit,
    private val onDelete: (FoodLog) -> Unit
) : RecyclerView.Adapter<Foodedit.FoodViewHolder>() {

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodName: TextView = view.findViewById(R.id.foodName)
        val editButton: ImageButton = view.findViewById(R.id.editButton)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_items, parent, false)

        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        val food = foods[position]

        holder.foodName.text =
            "${food.mealType}: ${food.foodName} (${food.foodCalories} cal)"

        holder.editButton.setOnClickListener {
            onEdit(food)
        }

        holder.deleteButton.setOnClickListener {
            onDelete(food)
        }
    }

    override fun getItemCount() = foods.size
}
