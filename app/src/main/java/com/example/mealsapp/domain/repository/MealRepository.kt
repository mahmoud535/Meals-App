package com.example.mealsapp.domain.repository

import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.model.ShoppingCartItem

interface MealRepository {
    suspend fun getMealsForDay(day: String): List<Meal>
    suspend fun insertMeal(meal: Meal)
    suspend fun getCartItems(): List<ShoppingCartItem>
    suspend fun addToCart(item: ShoppingCartItem)
    fun getImageList(): List<Int>
}