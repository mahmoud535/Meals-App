package com.example.mealsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.model.ShoppingCartItem

@Dao
interface MealDao {
    @Insert
    suspend fun addMeal(meal: Meal)

    @Query("SELECT * FROM meals WHERE day = :day")
    suspend fun getMealsForDay(day: String): List<Meal>

    @Insert
    suspend fun addItemToCart(item: ShoppingCartItem)

    @Query("SELECT * FROM shoppingCart WHERE userEmail = :userEmail")
    suspend fun getCartItem(userEmail: String): List<ShoppingCartItem>
}