package com.example.mealsapp.data.repository

import com.example.mealsapp.R
import com.example.mealsapp.data.local.MealDatabase
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.model.ShoppingCartItem
import com.example.mealsapp.domain.repository.MealRepository
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(private val mealDatabase: MealDatabase) : MealRepository {
    override suspend fun getMealsForDay(day: String): List<Meal> {
        return   mealDatabase.mealDao().getMealsForDay(day)
    }

    override suspend fun insertMeal(meal: Meal) {
        mealDatabase.mealDao().addMeal(meal)
    }

    override suspend fun getCartItems(userEmail: String): List<ShoppingCartItem> {
        return mealDatabase.mealDao().getCartItem(userEmail)
    }

    override suspend fun addToCart(item: ShoppingCartItem) {
        mealDatabase.mealDao().addItemToCart(item)
    }

    override fun getImageList() = arrayListOf(
        R.drawable.img16,
        R.drawable.img17,
        R.drawable.img4,
    )
}