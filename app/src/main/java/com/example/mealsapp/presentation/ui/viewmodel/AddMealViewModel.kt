package com.example.mealsapp.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealsapp.data.repository.MealRepositoryImpl
import com.example.mealsapp.domain.model.Meal
import com.example.mealsapp.domain.usecase.AddMealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMealViewModel @Inject constructor(
    private val addMealUseCase: AddMealUseCase
) : ViewModel() {

    fun addMeal(meal: Meal) {
        viewModelScope.launch {
            addMealUseCase(meal)
        }
    }
}