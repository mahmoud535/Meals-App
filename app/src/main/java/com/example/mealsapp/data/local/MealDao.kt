package com.example.mealsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.model.ShoppingCartItem

@Dao
interface MealDao {
    @Insert
    suspend fun insertMeal(meal: Meal)

    @Query("SELECT * FROM meals WHERE day = :day")
    suspend fun getMealsForDay(day: String): List<Meal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItemToCart(item: ShoppingCartItem)

    @Query("SELECT * FROM shoppingCart WHERE userEmail = :userEmail")
    suspend fun getAllCartItems(userEmail: String): List<ShoppingCartItem>

    @Query("DELETE FROM shoppingCart WHERE id = :itemId")
    suspend fun removeItemFromCart(itemId: Int)

    @Query("DELETE FROM shoppingCart")
    suspend fun clearCart()
}