package com.example.mealsapp.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.model.ShoppingCartItem
import com.example.mealsapp.domain.repository.MealRepository
import com.example.mealsapp.domain.usecase.AddToCartUseCase
import com.example.mealsapp.domain.usecase.GetCartItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    private val _cartState = MutableStateFlow<CartState>(CartState.Idle)
    val cartState: StateFlow<CartState> = _cartState

    fun addToCart(meal: Meal, userEmail: String) {
        viewModelScope.launch {
            try {
                val cartItems = getCartItemsUseCase(userEmail)
                if (cartItems.isEmpty()) {
                    val cartItem = ShoppingCartItem(
                        mealId = meal.id,
                        name = meal.name,
                        description = meal.description,
                        imageUri = meal.imageUri,
                        userEmail = userEmail
                    )
                    addToCartUseCase(cartItem)
                    _cartState.value = CartState.Success
                } else {
                    _cartState.value = CartState.Error("You can only add one meal per day!")
                }
            } catch (e: Exception) {
                _cartState.value = CartState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class CartState {
    data object Idle : CartState()
    data object Success : CartState()
    data class Error(val message: String) : CartState()
}
