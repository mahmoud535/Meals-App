package com.example.mealsapp.domain.usecase

import com.example.mealsapp.domain.model.ShoppingCartItem
import com.example.mealsapp.domain.repository.MealRepository
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend operator fun invoke(userEmail: String): List<ShoppingCartItem> { // Add userEmail parameter
        return mealRepository.getCartItems(userEmail) // Pass userEmail to repository
    }
}