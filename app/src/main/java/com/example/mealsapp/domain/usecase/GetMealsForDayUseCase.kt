package com.example.mealsapp.domain.usecase

import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.repository.MealRepository
import javax.inject.Inject

class GetMealsForDayUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend operator fun invoke(day: String): List<Meal> {
        return mealRepository.getMealsForDay(day)
    }
}