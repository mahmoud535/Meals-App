package com.example.mealsapp.domain.usecase

import com.example.mealsapp.domain.model.ShoppingCartItem
import com.example.mealsapp.domain.repository.MealRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend operator fun invoke(item: ShoppingCartItem) {
        mealRepository.addToCart(item)
    }
}