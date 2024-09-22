package com.example.mealsapp.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealsapp.domain.model.ShoppingCartItem
import com.example.mealsapp.domain.repository.MealRepository
import com.example.mealsapp.domain.usecase.GetCartItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase
) : ViewModel() {
    private val _cartItems = MutableStateFlow<List<ShoppingCartItem>?>(emptyList())
    val cartItems: StateFlow<List<ShoppingCartItem>?> get() = _cartItems

    fun loadCartItems(userEmail: String) { // Add userEmail parameter
        viewModelScope.launch {
            _cartItems.value = getCartItemsUseCase(userEmail) // Pass userEmail to use case
        }
    }
}
