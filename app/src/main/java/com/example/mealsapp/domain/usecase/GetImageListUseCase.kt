package com.example.mealsapp.domain.usecase

import com.example.mealsapp.domain.repository.MealRepository
import javax.inject.Inject

class GetImageListUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    operator fun invoke(): List<Int> {
        return mealRepository.getImageList()
    }
}