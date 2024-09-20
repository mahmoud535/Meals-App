package com.example.mealsapp.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealsapp.R
import com.example.mealsapp.databinding.MealItemLayoutBinding
import com.example.mealsapp.domain.model.Meal

class MealAdapter(
    private var meals: List<Meal>
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    var onListItemClick: OnListItemClick? = null

    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mealImageView: ImageView = itemView.findViewById(R.id.mealImageView)
        private val mealTitleTextView: TextView = itemView.findViewById(R.id.mealTitleTextView)
        private val mealDescriptionTextView: TextView = itemView.findViewById(R.id.mealDescriptionTextView)

        fun bind(meal: Meal) {
            Glide.with(itemView.context).load(meal.imageUri).into(mealImageView)
            mealTitleTextView.text = meal.name
            mealDescriptionTextView.text = meal.description

            itemView.setOnClickListener {
                onListItemClick?.onItemClick(meal)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meal_item_layout, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    override fun getItemCount(): Int = meals.size

    fun updateMeals(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }
}

