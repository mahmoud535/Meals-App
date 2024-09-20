package com.example.mealsapp.domain.usecase

import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.repository.MealRepository
import javax.inject.Inject

class AddMealUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend operator fun invoke(meal: Meal) {
        mealRepository.insertMeal(meal)
    }
}