package com.example.mealsapp.presentation.adapter

import com.example.mealsapp.domain.model.Meal

interface OnListItemClick {
    fun onItemClick(meal: Meal)
}